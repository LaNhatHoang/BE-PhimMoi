package Server.controller;

import Server.data.Film;
import Server.repository.CategoryRepository;
import Server.repository.FilmRepository;
import Server.service.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/film")
@RequiredArgsConstructor
public class FilmController {
    private final FilmRepository filmRepository;
    private final FilmService filmService;
    private final CategoryRepository categoryRepository;
    @GetMapping("/all")
    public ResponseEntity<List<Film>> getAll(){
        return ResponseEntity.ok(filmRepository.findAll());
    }

    @GetMapping("/phimle/limit/{limit}")
    public ResponseEntity<?> phimLeLimit(@PathVariable int limit){
        return ResponseEntity.ok(filmService.phimTypeLimit("movies", limit));
    }
    @GetMapping("/phimle/update/limit/{limit}")
    public ResponseEntity<?> phimLeUpdateLimit(@PathVariable int limit){
        return ResponseEntity.ok(filmService.phimTypeUpdateLimit("movies", limit));
    }
    @GetMapping("/phimle/ss/update/limit/{limit}")
    public ResponseEntity<?> phimLeSlideShowUpdateLimit(@PathVariable int limit){
        return ResponseEntity.ok(filmService.phimTypeSlideShowUpdateLimit("movies", limit));
    }
    @GetMapping("/phimbo/limit/{limit}")
    public ResponseEntity<?> phimBoLimit(@PathVariable int limit){
        return ResponseEntity.ok(filmService.phimTypeLimit("tvshows", limit));
    }
    @GetMapping("/phimbo/update/limit/{limit}")
    public ResponseEntity<?> phimBoUpdateLimit(@PathVariable int limit){
        return ResponseEntity.ok(filmService.phimTypeUpdateLimit("tvshows", limit));
    }
    @GetMapping("/phimbo/ss/update/limit/{limit}")
    public ResponseEntity<?> phimBoSlideShowUpdateLimit(@PathVariable int limit){
        return ResponseEntity.ok(filmService.phimTypeSlideShowUpdateLimit("tvshows", limit));
    }
    @GetMapping("/phimrap/limit/{limit}")
    public ResponseEntity<?> phimRapLimit(@PathVariable int limit){
        return ResponseEntity.ok(filmService.phimCategoryLimit("phimchieurap", limit));
    }
    @GetMapping("/trend/limit/{limit}")
    public ResponseEntity<?> phimTrendLimit(@PathVariable int limit){
        return ResponseEntity.ok(filmService.phimTrendLimit(limit));
    }
    @GetMapping("/phimle/trend/limit/{limit}")
    public ResponseEntity<?> phimLeTrendLimit(@PathVariable int limit){
        return ResponseEntity.ok(filmService.phimTypeTrendLimit("movies", limit));
    }
    @GetMapping("/phimbo/trend/limit/{limit}")
    public ResponseEntity<?> phimBoTrendLimit(@PathVariable int limit){
        return ResponseEntity.ok(filmService.phimTypeTrendLimit("tvshows", limit));
    }
    @GetMapping("/phimle/{url}")
    public ResponseEntity<?> phimLeUrl(@PathVariable String url){
        Film film = filmService.phimTypeUrl("movies", url);
        if(film == null){
            return ResponseEntity.status(404).body("");
        }
        return ResponseEntity.ok(film);
    }
    @GetMapping("/phimbo/{url}")
    public ResponseEntity<?> phimBoUrl(@PathVariable String url){
        Film film = filmService.phimTypeUrl("tvshows", url);
        if(film == null){
            return ResponseEntity.status(404).body("");
        }
        return ResponseEntity.ok(film);
    }
    @GetMapping("/phimle/page/{page}/{pageSize}")
    public ResponseEntity<?> phimLePage(@PathVariable int page, @PathVariable int pageSize){
        Page<Object[]> pageObject = filmService.phimTypePage("movies", page, pageSize);
        if(pageObject.getContent().size()==0){
            return ResponseEntity.status(404).body("");
        }
        return ResponseEntity.ok(pageObject);
    }
    @GetMapping("/phimbo/page/{page}/{pageSize}")
    public ResponseEntity<?> phimBoPage(@PathVariable int page, @PathVariable int pageSize){
        Page<Object[]> pageObject = filmService.phimTypePage("tvshows", page, pageSize);
        if(pageObject.getContent().size()==0){
            return ResponseEntity.status(404).body("");
        }
        return ResponseEntity.ok(pageObject);
    }
    @GetMapping("/category")
    public ResponseEntity<?> category(){
        return ResponseEntity.ok(categoryRepository.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Film> getById(@PathVariable int id){
        Film film = filmRepository.findById(id);
        return ResponseEntity.ok(film);
    }
    @PostMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable int id){
        Film film = filmRepository.findById(id);
        film.setUpdateAt(new Date(System.currentTimeMillis()));
        filmRepository.save(film);
        return ResponseEntity.ok("Update");
    }
    @PostMapping("/create/{id}")
    public ResponseEntity<String> create(@PathVariable int id){
        Film film = filmRepository.findById(id);
        film.setCreateAt(new Date(System.currentTimeMillis()));
        filmRepository.save(film);
        return ResponseEntity.ok("Create");
    }
}
