package Server.controller;

import Server.config.ImportData;
import Server.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/file/")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

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
