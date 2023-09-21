package Server.controller;

import Server.config.ImportData;
import Server.entityFilm.Film;
import Server.repository.FilmRepository;
import Server.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final FilmRepository filmRepository;
    private final FileService fileService;
    private final ImportData importData;
    @GetMapping("/film/update/{id}")
    public ResponseEntity<String> update(@PathVariable int id){
        Film film = filmRepository.findById(id);
        if(film==null){
            return ResponseEntity.ok("Can not find phim with id "+id);
        }
        film.setUpdateAt(new Date(System.currentTimeMillis()));
        filmRepository.save(film);
        return ResponseEntity.ok("Update success !");
    }
    @GetMapping("/film/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        Film film = filmRepository.findById(id);
        if(film==null){
            return ResponseEntity.ok("Can not find phim with id "+id);
        }
        filmRepository.delete(film);
        return ResponseEntity.ok("Delete success !");
    }
    @GetMapping("/image/download")
    public ResponseEntity<?> upload() throws IOException {
        return ResponseEntity.ok(fileService.download());
    }
    @PostMapping("/importDataBlog")
    public ResponseEntity<?> importDataBlog(@RequestParam("file") MultipartFile file){
        return ResponseEntity.ok(importData.importDataBlog(file));
    }
    @PostMapping("/importDataFilm")
    public ResponseEntity<?> importDataFilm(@RequestParam("file")MultipartFile file){
        return ResponseEntity.ok(importData.importDataFilm(file));
    }
}
