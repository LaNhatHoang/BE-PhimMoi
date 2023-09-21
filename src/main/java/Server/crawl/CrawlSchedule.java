package Server.crawl;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CrawlSchedule {
    private final CrawlData crawlData;
//    @Scheduled(initialDelay = 10000, fixedDelay = 5000)
//    private void crawl(){
//        System.out.println("crawl each 5 minute");
//    }
}
