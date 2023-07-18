package Server.controller;

import Server.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/file/")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @GetMapping("/download")
    public ResponseEntity<?> upload() throws IOException {
        return ResponseEntity.ok(fileService.download());
    }
    @GetMapping("/image/{fileName}")
    public ResponseEntity<?> getImage(@PathVariable String fileName){
        try{
            byte[] bytes =  fileService.readImage(fileName);
            return  ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
        }catch (Exception e){
            return ResponseEntity.status(404).body("");
        }
    }
    @GetMapping("/image500/{fileName}")
    public ResponseEntity<byte[]> getImage500(@PathVariable String fileName){
        try{
            byte[] bytes =  fileService.readImage500(fileName);
            return  ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
        }catch (Exception e){
            return ResponseEntity.noContent().build();
        }
    }
}
