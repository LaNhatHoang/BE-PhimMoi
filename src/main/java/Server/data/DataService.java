package Server.data;

import Server.repository.*;
import Server.service.FilmService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;
import java.security.SecureRandom;
import java.util.*;

@Component
@RequiredArgsConstructor
public class DataService implements CommandLineRunner {
    private final FilmRepository filmRepository;
    private final CategoryRepository categoryRepository;
    private final ServerRepository serverRepository;
    private final PartRepository partRepository;
    private final DirectorRepository directorRepository;
    private final ActorRepository actorRepository;
    private final ServerPartRepository serverPartRepository;
    @Override
    @Transactional
    public void run(String... args) throws Exception {
//        try(FileReader reader = new FileReader("D:/Nodejs/crawl/data.json")) {
//            JsonParser jsonParser = new JsonParser();
//            JsonElement jsonElement = jsonParser.parse(reader);
//            JsonArray jsonArray = jsonElement.getAsJsonArray();
//
//            Gson gson = new Gson();
//            for (int i = jsonArray.size() - 1; i >= 0; i--) {
//                FilmJson filmJson = gson.fromJson(jsonArray.get(i), FilmJson.class);
//                SecureRandom random = new SecureRandom();
//                Film film = filmRepository.findByUrl(filmJson.getUrl());
//                film.setReview(random.nextInt(100));
//                film.setUrlTrailer(filmJson.getUrlTrailer());
//                System.out.println(filmJson.getUrl());
//            }
//        }catch (Exception e){
//
//        }



//        try(FileReader reader = new FileReader("D:/Nodejs/crawl/data.json")){
//            JsonParser jsonParser = new JsonParser();
//            JsonElement jsonElement = jsonParser.parse(reader);
//            JsonArray jsonArray = jsonElement.getAsJsonArray();
//
//            Gson gson = new Gson();
//            for(int i=jsonArray.size()-1;i>=0;i--){
//                FilmJson filmJson = gson.fromJson(jsonArray.get(i), FilmJson.class);
//                SecureRandom random = new SecureRandom();
//                Film film = Film.builder().name(filmJson.getName()).nameEng(filmJson.getNameEng()).url(filmJson.getUrl())
//                        .year(filmJson.getYear()).sub(filmJson.getSub())
//                        .type(filmJson.getType()).urlImage(filmJson.getUrlImage())
//                        .time(filmJson.getTime()).country(filmJson.getCountry())
//                        .rated(filmJson.getRated()).star(random.nextInt(11)).share(random.nextInt(100))
//                        .legion(filmJson.getLegion()).descriptions(String.join("<br>", filmJson.getDescriptions()))
//                        .createAt(new Date(System.currentTimeMillis()))
//                        .updateAt(new Date(System.currentTimeMillis()))
//                        .categorys(new ArrayList<>()).servers(new ArrayList<>())
//                        .parts(new ArrayList<>()).directors(new ArrayList<>())
//                        .actors(new ArrayList<>()).build();
//                List<Category> listCtg = filmJson.getCategorys();
//                List<Server> serverList = filmJson.getServers();
//                List<Part> partList = filmJson.getParts();
//                List<Director> directorList = filmJson.getDirectors();
//                List<Actor> actorList = filmJson.getActors();
//
//                System.out.println(film.getName());
//
//                for(Category c: listCtg){
//                    Category category = categoryRepository.findByName(c.getName()) ;
//                    if(category == null){
//                        category = Category.builder().name(c.getName()).type(c.getType()).films(new ArrayList<>()).build();
//                    }
//                    film.getCategorys().add(category);
//                    category.getFilms().add(film);
//                    filmRepository.save(film);
//                    categoryRepository.save(category);
//                }
//                if(film.getType().equals("movies")){
//                    for(Server s: serverList){
//                        Server server = Server.builder().name(s.getName()).type(s.getType()).url(s.getUrl())
//                                .film(film).build();
//                        film.getServers().add(server);
//                        serverRepository.save(server);
//                        filmRepository.save(film);
//                    }
//                }else{
//                    for(Part p: partList){
//                        Part part = Part.builder().name(p.getName()).servers(new ArrayList<>()).film(film).build();
//                        if(p.getServers().size()==0){
//                            film.getParts().add(part);
//                            partRepository.save(part);
//                            filmRepository.save(film);
//                        }
//                        else{
//                            for(ServerPart sp: p.getServers()){
//                                ServerPart serverPart = ServerPart.builder().name(sp.getName()).type(sp.getType()).url(sp.getUrl())
//                                        .part(part).build();
//                                part.getServers().add(serverPart);
//                                film.getParts().add(part);
//                                serverPartRepository.save(serverPart);
//                                partRepository.save(part);
//                                filmRepository.save(film);
//                            }
//                        }
//                    }
//                }
//                for(Director d: directorList){
//                    Director director = Director.builder().name(d.getName()).urlImg(d.getUrlImg()).role(d.getRole())
//                            .film(film).build();
//                    film.getDirectors().add(director);
//                    directorRepository.save(director);
//                    filmRepository.save(film);
//                }
//                for(Actor a: actorList){
//                    Actor actor = Actor.builder().name(a.getName()).urlImg(a.getUrlImg()).role(a.getRole())
//                            .film(film).build();
//                    film.getActors().add(actor);
//                    actorRepository.save(actor);
//                    filmRepository.save(film);
//                }
//                System.out.println(i);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }
}
