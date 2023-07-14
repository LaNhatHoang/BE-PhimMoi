package Server.service;

import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.StreamUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {
    private final Path storageFolder = Paths.get("uploads");
    public FileService(){
        try{
            Files.createDirectories(storageFolder);
        }catch (Exception e){
            throw new RuntimeException("Connot init storage");
        }
    }
    public byte[] readFileContent(String fileName){
        try {
            Path file = storageFolder.resolve(fileName);
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
