package Server.controller;

import Server.entity.User;
import Server.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user){
        return ResponseEntity.ok(userService.register(user));
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user, HttpServletRequest request){
        return ResponseEntity.ok(userService.login(user, request));
    }
}
