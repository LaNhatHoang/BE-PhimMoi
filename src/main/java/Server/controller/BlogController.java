package Server.controller;

import Server.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/blog")
@RequiredArgsConstructor
public class BlogController {
    private final BlogRepository blogRepository;
    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(blogRepository.findAll());
    }
}
