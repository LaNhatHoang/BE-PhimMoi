package Server.controller;

import Server.entityFilm.Blog;
import Server.entityFilm.Film;
import Server.repository.BlogRepository;
import Server.repository.FilmRepository;
import Server.service.FilmService;
import com.google.gson.JsonObject;
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
    private final BlogRepository blogRepository;

    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(filmRepository.findAll());
    }
    @GetMapping("/{type}/limit/{limit}")
    public ResponseEntity<?> phimTypeLimit(@PathVariable String type, @PathVariable int limit){
        return ResponseEntity.ok(filmService.phimTypeLimit(type, limit));
    }
    @GetMapping("/{type}/update/limit/{limit}")
    public ResponseEntity<?> phimTypeUpdateLimit(@PathVariable String type, @PathVariable int limit){
        return ResponseEntity.ok(filmService.phimTypeUpdateLimit(type, limit));
    }
    @GetMapping("/{type}/ss/update/limit/{limit}")
    public ResponseEntity<?> phimTypeSlideShowUpdateLimit(@PathVariable String type, @PathVariable int limit){
        return ResponseEntity.ok(filmService.phimTypeSlideShowUpdateLimit(type, limit));
    }
    @GetMapping("/category/{category}/limit/{limit}")
    public ResponseEntity<?> phimCategoryLimit(@PathVariable String category, @PathVariable int limit){
        return ResponseEntity.ok(filmService.phimCategoryLimit(category, limit));
    }
    @GetMapping("/trend/limit/{limit}")
    public ResponseEntity<?> phimTrendLimit(@PathVariable int limit){
        return ResponseEntity.ok(filmService.phimTrendLimit(limit));
    }
    @GetMapping("/{type}/trend/limit/{limit}")
    public ResponseEntity<?> phimTypeTrendLimit(@PathVariable String type, @PathVariable int limit){
        return ResponseEntity.ok(filmService.phimTypeTrendLimit(type, limit));
    }
    @GetMapping("/{type}/random/limit/{limit}")
    public ResponseEntity<?> phimTypeRandom(@PathVariable String type, @PathVariable int limit){
        return ResponseEntity.ok(filmService.phimTypeRandom(type, limit));
    }
    @GetMapping("/random")
    public ResponseEntity<?> phimRandom(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", "Vệ Binh Dải Ngân Hà 3");
        jsonObject.addProperty("year", 2023);
        jsonObject.addProperty("urlImage", "https://image.tmdb.org/t/p/w500/ypKROTROwMdbUteTMG6CP2Y4yug.jpg");
        jsonObject.addProperty("url", "ve-binh-dai-ngan-ha-3");
        jsonObject.addProperty("type", "movies");
        return ResponseEntity.ok(jsonObject.toString());
    }
    @GetMapping("/{type}/{url}")
    public ResponseEntity<?> phimTypeUrl(@PathVariable String type, @PathVariable String url){
        Film film = filmService.phimTypeUrl(type, url);
        if(film == null){
            return ResponseEntity.status(404).body("");
        }
        return ResponseEntity.ok(film);
    }
    @GetMapping("/{type}/page/{page}/{pageSize}")
    public ResponseEntity<?> phimTypePage(@PathVariable String type, @PathVariable int page, @PathVariable int pageSize){
        if(page<0){
            return ResponseEntity.status(404).body("");
        }
        Page<Object[]> pageObject = filmService.phimTypePage(type, page, pageSize);
        if(page >= pageObject.getTotalPages()){
            return ResponseEntity.status(404).body("");
        }
        return ResponseEntity.ok(pageObject);
    }
    @GetMapping("/category/{category}/page/{page}/{pageSize}")
    public ResponseEntity<?> phimCategoryPage(@PathVariable String category, @PathVariable int page, @PathVariable int pageSize){
        if(page<0){
            return ResponseEntity.status(404).body("");
        }
        Page<Object[]> pageObject = filmService.phimCategoryPage(category, page, pageSize);
        if(page >= pageObject.getTotalPages()){
            return ResponseEntity.status(404).body("");
        }
        return ResponseEntity.ok(pageObject);
    }
    @GetMapping("/legion/{legion}/page/{page}/{pageSize}")
    public ResponseEntity<?> phimLegionPage(@PathVariable String legion, @PathVariable int page, @PathVariable int pageSize){
        if(page<0){
            return ResponseEntity.status(404).body("");
        }
        Page<Object[]> pageObject = filmService.phimLegionPage(legion, page, pageSize);
        if(page >= pageObject.getTotalPages()){
            return ResponseEntity.status(404).body("");
        }
        return ResponseEntity.ok(pageObject);
    }
    @GetMapping("/year/{year}/page/{page}/{pageSize}")
    public ResponseEntity<?> phimYearPage(@PathVariable int year, @PathVariable int page, @PathVariable int pageSize){
        if(page<0){
            return ResponseEntity.status(404).body("");
        }
        Page<Object[]> pageObject = filmService.phimYearPage(year, page, pageSize);
        if(page >= pageObject.getTotalPages()){
            return ResponseEntity.status(404).body("");
        }
        return ResponseEntity.ok(pageObject);
    }
    @GetMapping("/search/page/{page}/{pageSize}")
    public ResponseEntity<?> phimSearch(@PathVariable int page, @PathVariable int pageSize, @RequestParam(value = "search", defaultValue = "") String searchValue){
        if(searchValue.equals("")){
            return ResponseEntity.status(404).body("");
        }
        if(page<0){
            return ResponseEntity.status(404).body("");
        }
        Page<Object[]> pageObject = filmService.phimSearch(searchValue, page, pageSize);
        if(pageObject.getTotalPages()==0){
            if(page > 0){
                return ResponseEntity.status(404).body("");
            }
            return ResponseEntity.ok(pageObject);
        }
        if(page >= pageObject.getTotalPages()){
            return ResponseEntity.status(404).body("");
        }
        return ResponseEntity.ok(pageObject);
    }
    @GetMapping("/blog/all")
    public ResponseEntity<?> blogAll(){
        return ResponseEntity.ok(filmService.blogAll());
    }
    @GetMapping("/blog/limit/{limit}")
    public ResponseEntity<?> blogLimit(@PathVariable int limit){
        return ResponseEntity.ok(filmService.blogLimit(limit));
    }
    @GetMapping("/blog/{url}")
    public ResponseEntity<?> blogUrl(@PathVariable String url){
        Blog blog = blogRepository.findByUrl(url);
        if(blog==null){
            return ResponseEntity.status(404).body("");
        }
        return ResponseEntity.ok(blog);
    }

}
