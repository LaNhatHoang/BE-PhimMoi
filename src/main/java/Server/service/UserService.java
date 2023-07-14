package Server.service;

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
    private final HashMap<String, LoginFailed> map;
    private final long timeBlock = 10 * 1000L;
    private final int maxLoginFailed = 5;

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
            return "tk ko ton tai";
        }
        if(!user.getPassword().equals(u.getPassword())){
            if(map.containsKey(ip)){
                LoginFailed loginFailed = map.get(ip);
                if(!loginFailed.isBlock()){
                    int countLoginFailed = loginFailed.getCount();
                    if(countLoginFailed < maxLoginFailed -1 ){
                        loginFailed.setCount(countLoginFailed+1);
                        map.replace(ip, loginFailed);
                        return "sai tk" + Integer.toString(countLoginFailed+1);
                    }
                    else{
                        loginFailed.setBlock(true);
                        loginFailed.setTimeBlock(new Date(System.currentTimeMillis() + timeBlock));
                        loginFailed.setCount(maxLoginFailed);
                        map.replace(ip,loginFailed);
                        return "khoa tk 10 giay";
                    }
                }
                long time = loginFailed.getTimeBlock().getTime() - System.currentTimeMillis();
                if(time>0){
                    return "khoa tk " + Long.toString(time);
                }
                loginFailed.setCount(0);
                loginFailed.setBlock(false);
                loginFailed.setTimeBlock(new Date(System.currentTimeMillis()));
                map.replace(ip, loginFailed);
                return "sai mk 1 lan";
            }
            else{
                LoginFailed loginFailed = new LoginFailed(0,false,new Date(System.currentTimeMillis()));
                map.put(ip,loginFailed);
                System.out.println(ip);
                System.out.println(loginFailed.getCount());
                System.out.println(loginFailed.getTimeBlock());
            }
            return "sai mk";
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
