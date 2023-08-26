package uz.ecommerce.fileservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.ecommerce.commons.model.response.FileResponse;
import uz.ecommerce.fileservice.service.FileService;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/file")
public class FileController {
    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<FileResponse> upload(@RequestParam("file") MultipartFile multipartFile) {
        return ok(fileService.upload(multipartFile));
    }

    @GetMapping("/{filename}")
    public ResponseEntity<FileResponse> getFileByName(@PathVariable String filename){
        return ok(fileService.getFileByName(filename));
    }

    @GetMapping("/open/{filename}")
    public ResponseEntity<byte[]> openFileByName(@PathVariable String filename){
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(fileService.openFileByName(filename));
    }

    @DeleteMapping("/{fileId}")
    @ResponseStatus(OK)
    public void delete(@PathVariable int fileId){
        fileService.delete(fileId);
    }

}
