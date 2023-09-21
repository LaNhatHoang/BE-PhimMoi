package Server.config;

import Server.crawl.CrawlThread;
import Server.entity.User;
import Server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class InitData implements CommandLineRunner {
    private final UserRepository userRepository;

    @Override
    public void run(String... args){
        System.out.println("cmd run");
        User admin = userRepository.findByUsername("admin");
       if(admin == null){
           admin = User.builder().username("admin").password("nhathoang").roles(List.of("USER", "ADMIN")).build();
           userRepository.save(admin);
       }
    }
}
