package Server.service;

import Server.entityFilm.Film;
import Server.repository.FilmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FilmRepository filmRepository;
    private final Path storageImage = Paths.get("image");
    private final Path storageImage500 = Paths.get("image500");

    public String download() throws IOException {
        try{
            Files.createDirectories(storageImage);
            Files.createDirectories(storageImage500);
        }catch (Exception e){

        }
        List<Film> filmList = filmRepository.findAll();
        for(Film film:filmList){
            URL url = new URL(film.getUrlImagePhimmoi());
            String randomName = UUID.randomUUID().toString() + ".jpg";
            Path destinationFilePath = storageImage.resolve(Paths.get(randomName)).normalize().toAbsolutePath();
            InputStream inputStream = url.openStream();
            Files.copy(inputStream, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
            film.setUrlImage(randomName);
            filmRepository.save(film);
            System.out.println(film.getId() + " " + film.getName());
        }
        return "upload thanh cong";
    }
    public byte[] readImage(String fileName){
        try {
            Path file = storageImage.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()){
                byte[] bytes = StreamUtils.copyToByteArray(resource.getInputStream());
                return bytes;
            }
            else{
                throw new RuntimeException("Could not read file");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public byte[] readImage500(String fileName){
        try {
            Path file = storageImage500.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()){
                byte[] bytes = StreamUtils.copyToByteArray(resource.getInputStream());
                return bytes;
            }
            else{
                throw new RuntimeException("Could not read file");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
