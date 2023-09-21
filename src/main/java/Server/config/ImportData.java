package Server.config;

import Server.entityFilm.*;
import Server.repository.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ImportData {
    private final FilmRepository filmRepository;
    private final CategoryRepository categoryRepository;
    private final ServerRepository serverRepository;
    private final PartRepository partRepository;
    private final DirectorRepository directorRepository;
    private final ActorRepository actorRepository;
    private final ServerPartRepository serverPartRepository;
    private final BlogRepository blogRepository;

    public String importDataBlog(MultipartFile file){
        if(file.isEmpty()){
            return "File is empty";
        }
        try{
            Path tempFile = Files.createTempFile("data-blog",".json");
            Files.copy(file.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);
            File jsonFile = tempFile.toFile();
            FileReader reader = new FileReader(jsonFile);
            JsonParser jsonParser = new JsonParser();
            JsonElement jsonElement = jsonParser.parse(reader);
            JsonArray jsonArray = jsonElement.getAsJsonArray();

            Gson gson = new Gson();
            for (int i = 0; i < jsonArray.size(); i++) {
                Blog blog = gson.fromJson(jsonArray.get(i), Blog.class);
                Blog b = new Blog();
                b.setName(blog.getName());
                b.setDescription(blog.getDescription());
                b.setUrl(blog.getUrl());
                b.setContent(blog.getContent());
                b.setCreatAt(new Date(System.currentTimeMillis()));
                blogRepository.save(b);
                System.out.println("----- "+blog.getName());
            }
            return "Import data blog success";
        }catch (Exception e){
            System.out.println(e);
            return e.getMessage();
        }
    }
    public String importDataFilm(MultipartFile file){
        if(file.isEmpty()){
            return "File is empty";
        }
        try{
            Path tempFile = Files.createTempFile("data-film",".json");
            Files.copy(file.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);
            File jsonFile = tempFile.toFile();
            FileReader reader = new FileReader(jsonFile);
            JsonParser jsonParser = new JsonParser();
            JsonElement jsonElement = jsonParser.parse(reader);
            JsonArray jsonArray = jsonElement.getAsJsonArray();

            Gson gson = new Gson();
            for(int i= jsonArray.size()-1;i>=0;i--){
                FilmJson filmJson = gson.fromJson(jsonArray.get(i), FilmJson.class);
                SecureRandom random = new SecureRandom();
                Film film = Film.builder().name(filmJson.getName()).nameEng(filmJson.getNameEng()).url(filmJson.getUrl())
                        .year(filmJson.getYear()).sub(filmJson.getSub()).type(filmJson.getType()).urlImagePhimmoi(filmJson.getUrlImage())
                        .time(filmJson.getTime()).country(filmJson.getCountry()).review(random.nextInt(100)).urlTrailer(filmJson.getUrlTrailer())
                        .rated(filmJson.getRated()).star(random.nextInt(6)+5).share(random.nextInt(99)+1)
                        .legion(filmJson.getLegion()).descriptions(String.join("<br>", filmJson.getDescriptions()))
                        .createAt(new Date(System.currentTimeMillis())).updateAt(new Date(System.currentTimeMillis()))
                        .categories(new HashSet<>()).servers(new ArrayList<>()).parts(new ArrayList<>()).directors(new ArrayList<>())
                        .actors(new ArrayList<>()).build();
                List<Category> listCtg = filmJson.getCategorys();
                List<Server> serverList = filmJson.getServers();
                List<Part> partList = filmJson.getParts();
                List<Director> directorList = filmJson.getDirectors();
                List<Actor> actorList = filmJson.getActors();

                for(Category c: listCtg){
                    Category category = categoryRepository.findByName(c.getName()) ;
                    if(category == null){
                        category = Category.builder().name(c.getName()).type(c.getType()).films(new HashSet<>()).build();
                    }
                    film.getCategories().add(category);
                    category.getFilms().add(film);
                    categoryRepository.save(category);
//                    filmRepository.save(film);
                }
                filmRepository.save(film);
                if(film.getType().equals("movies")){
                    for(Server s: serverList){
                        Server server = Server.builder().name(s.getName()).type(s.getType()).url(s.getUrl())
                                .film(film).build();
                        film.getServers().add(server);
                        serverRepository.save(server);
                        filmRepository.save(film);
                    }
                }else{
                    for(Part p: partList){
                        Part part = Part.builder().name(p.getName()).servers(new ArrayList<>()).film(film).build();
                        if(p.getServers().size()==0){
                            film.getParts().add(part);
                            partRepository.save(part);
                            filmRepository.save(film);
                        }
                        else{
                            for(ServerPart sp: p.getServers()){
                                ServerPart serverPart = ServerPart.builder().name(sp.getName()).type(sp.getType()).url(sp.getUrl())
                                        .part(part).build();
                                part.getServers().add(serverPart);
                                film.getParts().add(part);
                                partRepository.save(part);
                                serverPartRepository.save(serverPart);
                                filmRepository.save(film);
                            }
                        }
                    }
                }
                for(Director d: directorList){
                    Director director = Director.builder().name(d.getName()).urlImg(d.getUrlImg()).role(d.getRole())
                            .film(film).build();
                    film.getDirectors().add(director);
                    directorRepository.save(director);
                    filmRepository.save(film);
                }
                for(Actor a: actorList){
                    Actor actor = Actor.builder().name(a.getName()).urlImg(a.getUrlImg()).role(a.getRole())
                            .film(film).build();
                    film.getActors().add(actor);
                    actorRepository.save(actor);
                    filmRepository.save(film);
                }
                System.out.println( i + " " + film.getName());
            }
            return "Import data phim success";
        }catch (Exception e){
            System.out.println(e);
            return e.getMessage();
        }
    }
}
