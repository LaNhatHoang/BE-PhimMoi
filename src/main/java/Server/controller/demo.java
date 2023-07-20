package Server.controller;

import Server.entity.AccessToken;
import Server.entity.User;
import Server.repository.AccessTokenRepository;
import Server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class demo {
    private final UserRepository userRepository;
    private final AccessTokenRepository accessTokenRepository;
    @GetMapping("/")
    public ResponseEntity<String> get(){
        return ResponseEntity.ok("Hello world");
    }
    @GetMapping("/admin")
    public ResponseEntity<String> admin(){
        return ResponseEntity.ok("admin");
    }
    @GetMapping("/user")
    public ResponseEntity<String> user(){
        return ResponseEntity.ok("user");
    }

}
