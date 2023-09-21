package Server.service;

import Server.botTele.MyBot;
import Server.entity.LoginFailed;
import Server.entity.User;
import Server.jwt.JwtService;
import Server.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final MyBot myBot;

    public String register(User user){
        User user1 = userRepository.findByUsername(user.getUsername());
        if(user1 != null){
            return "tk da ton tai";
        }
        User user2 = User.builder().username(user.getUsername())
                .password(user.getPassword())
                .roles(List.of("USER")).build();
        userRepository.save(user2);
        return "dang ky thanh cong";
    }

    public String login(User user, HttpServletRequest request){
        String ip = request.getRemoteAddr();
        User u = userRepository.findByUsername(user.getUsername());
        if(u == null){
            return "Account is not exist";
        }
        if(!user.getPassword().equals(u.getPassword())){
            myBot.sendText("Login failed in ip "+ip+" | "+new Date(System.currentTimeMillis()).toString());
            return "Wrong username or password";
        }
        String accessToken = jwtService.generateAccessToken(u.getUsername());
        String refreshToken = jwtService.generateRefreshToken(u.getUsername());
        jwtService.revokeAccessToken(u);
        jwtService.revokeRefreshToken(u);
        jwtService.saveAccessToken(accessToken, u);
        jwtService.saveRefreshToken(refreshToken, u);

        return accessToken;
    }
}
