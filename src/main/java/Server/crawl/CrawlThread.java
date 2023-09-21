package Server.crawl;

import Server.entityFilm.*;
import Server.repository.*;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.*;

@Component
@RequiredArgsConstructor
public class CrawlThread extends Thread {
    private final CrawlData crawlData;

    @Override
    public void run() {
        System.out.println("Crawl data thread start");
        crawlData.crawlData();
    }

}
