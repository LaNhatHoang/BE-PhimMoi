package Server.crawl;

import Server.botTele.MyBot;
import Server.entityFilm.*;
import Server.repository.*;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

import java.security.SecureRandom;
import java.util.*;

@Component
@RequiredArgsConstructor
public class CrawlData {
    private final FilmRepository filmRepository;
    private final CategoryRepository categoryRepository;
    private final ServerRepository serverRepository;
    private final PartRepository partRepository;
    private final ServerPartRepository serverPartRepository;
    private final DirectorRepository directorRepository;
    private final ActorRepository actorRepository;
    private final MyBot myBot;
    private static final Logger logger = LoggerFactory.getLogger(CrawlData.class);

    public void crawlData(){
        Document document = new Document("https://phimmoiyyy.net/?s=");
        try{
            document = Jsoup.connect("https://phimmoiyyy.net/?s=").get();
        }catch (Exception e){
            System.out.println("Get html failed");
            return;
        }
        Film film = filmRepository.findTopByOrderByIdDesc();
        if(film!=null){
            System.out.println(film.getId()+ " " +film.getName());
        }
        Elements listLinks = document.select(".result-item");
        // list new film
        List<Element> listNewFilm = new ArrayList<>();
        for(int i=0;i<listLinks.size();i++){
            String name = listLinks.get(i).select(".title > a").first().text();
            if(name.equals(film.getName())){
                break;
            }
//            myBot.sendText("Co phim moi: "+name);
            logger.info(name);
            listNewFilm.add(listLinks.get(i));
        }
        System.out.println(listNewFilm.size());
        update(listNewFilm);
    }
    private void update(List<Element> list){
        for(int i=list.size()-1;i>=0;i--){
            String href = list.get(i).select(".title > a").first().attr("href");
            String[] arrString = href.split("/");
            String url = arrString[arrString.length-1];
            Film film = filmRepository.findByUrl(url);
            if(film!=null){
//                filmRepository.delete(film);
                System.out.println("Delete : "+film.getName());
//                crawl(href);
            }
            else{
//                crawl(href);
            }
        }
    }
    private void crawl(String href) {
        String[] arrString = href.split("/");
        String type = arrString[arrString.length-2];
        if(type.equals("phim-le")){
            crawlPhimLe(href);
        }
        else{
            crawlPhimBo(href);
        }
    }
    @Transactional
    private void crawlPhimLe(String href){
        Document document = new Document(href);
        try{
           document = Jsoup.connect(href).get();
        }catch (Exception e){
            System.out.println("Get html failed");
            return;
        }
        String name = document.selectFirst(".sheader > .data > h1").text();
        String nameEng = document.selectFirst(".sheader > .data > .extra > .valor").text();
        String[] arrHrefString = href.split("/");
        String url = arrHrefString[arrHrefString.length-1];
        String timeString = document.selectFirst(".sheader > .data > .extra > .runtime").text();
        int time = Integer.parseInt(timeString.substring(0,timeString.length()-5));
        String country = document.selectFirst(".sheader > .data > .extra > .country").text();
        String rated = document.selectFirst(".sheader > .data > .extra > .rated").text();
        String urlImage = document.selectFirst(".sheader > .poster > img").attr("src");
        Element elmTrailer = document.selectFirst("#trailer iframe");
        String urlTrailer = "";
        if(elmTrailer!=null){
            urlTrailer = elmTrailer.attr("src");
        }
        Elements listDesc = document.select(".content.right > #info > .wp-content > p");
        List<String> listStrDesc = new ArrayList<>();
        for(Element element:listDesc){
            listStrDesc.add(element.text());
        }
        String description = String.join("<br>",listStrDesc);
        SecureRandom random = new SecureRandom();
        Film film = Film.builder().name(name).nameEng(nameEng).url(url).year(2023).sub("HD Vietsub").type("movies")
                .view(0).star(random.nextInt(6)+5).share(random.nextInt(99)+1).review(random.nextInt(100))
                .urlImagePhimmoi(urlImage).time(time).country(country).legion("aumy").rated(rated).urlTrailer(urlTrailer)
                .descriptions(description).createAt(new Date(System.currentTimeMillis())).updateAt(new Date(System.currentTimeMillis()))
                .categories(new HashSet<>()).servers(new ArrayList<>()).parts(new ArrayList<>()).directors(new ArrayList<>()).actors(new ArrayList<>())
                .build();
        Elements elmCategory = document.select(".data > .sgeneros > a");
        for(Element element:elmCategory){
            String nameCategory = element.text();
            Category category = categoryRepository.findByName(nameCategory);
            if(category==null){
                System.out.println("Ko tim thay category");;
            }
            else{
                film.getCategories().add(category);
                category.getFilms().add(film);
                categoryRepository.save(category);
            }
        }
        filmRepository.save(film);
        Elements elmServer = document.select("#playeroptionsul > li");
        for(Element element:elmServer){
            String nameServer = element.selectFirst(".title").text();
            String dataPost = element.attr("data-post");
            String dataNume = element.attr("data-nume");
            // call data
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.put("action", Collections.singletonList("doo_player_ajax"));
            formData.put("post", Collections.singletonList(dataPost));
            formData.put("nume", Collections.singletonList(dataNume));
            formData.put("type", Collections.singletonList("movie"));
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formData, headers);
            JSONObject jsonObject = new JSONObject(restTemplate.postForObject("https://phimmoiyyy.net/wp-admin/admin-ajax.php", request, String.class));
            Server server = Server.builder().name(nameServer).url(jsonObject.getString("embed_url"))
                    .type(jsonObject.getString("type")).film(film).build();
            film.getServers().add(server);
            serverRepository.save(server);
            filmRepository.save(film);
        }
        Elements elmDirector = document.select("#cast > .persons").eq(1).select(".person");
        for(Element element:elmDirector){
            String nameDirector = element.selectFirst(".data > .name > a").text();
            String roleDirector = element.selectFirst(".data > .caracter").text();
            String urlImageDirector = element.selectFirst(".img > a > img").attr("src");
            Director director = Director.builder().name(nameDirector).role(roleDirector).urlImg(urlImageDirector).film(film).build();
            film.getDirectors().add(director);
            directorRepository.save(director);
            filmRepository.save(film);
        }
        Elements elmActor = document.select("#cast > .persons").eq(2).select(".person");
        for(Element element:elmActor){
            String nameActor = element.selectFirst(".data > .name > a").text();
            String roleActor = element.selectFirst(".data > .caracter").text();
            String urlImageActor = element.selectFirst(".img > a > img").attr("src");
            Actor actor = Actor.builder().name(nameActor).role(roleActor).urlImg(urlImageActor).film(film).build();
            film.getActors().add(actor);
            actorRepository.save(actor);
            filmRepository.save(film);
        }
        System.out.println("Crawl phim le: "+name);
    }

    private void crawlPhimBo(String href){
        System.out.println(href);
    }


}
