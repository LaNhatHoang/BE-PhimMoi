package Server.data;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class Download implements CommandLineRunner {
    private final Path storageFolder = Paths.get("uploads");
    public Download(){
        try{
            Files.createDirectories(storageFolder);
        }catch (Exception e){
            throw new RuntimeException("Cannot init storage");
        }
    }
    @Override
    public void run(String... args) throws Exception {
//        try(FileReader reader = new FileReader("D:/Nodejs/crawl/data.json")){
//            JsonParser jsonParser = new JsonParser();
//            JsonElement jsonElement = jsonParser.parse(reader);
//            JsonArray jsonArray = jsonElement.getAsJsonArray();
//
//            Gson gson = new Gson();
//            for(int i=0;i<jsonArray.size();i++){
//                FilmJson filmJson = gson.fromJson(jsonArray.get(i), FilmJson.class);
//                URL url = new URL(filmJson.getUrlImage());
//                String fileName = url.getFile().substring(28);
//                Path destinationFilePath = storageFolder.resolve(Paths.get(fileName)).normalize().toAbsolutePath();
//                InputStream inputStream = url.openStream();
//                Files.copy(inputStream, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
//                System.out.println(i + " " + fileName);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }

    }
}
