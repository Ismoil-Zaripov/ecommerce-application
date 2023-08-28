package uz.ecommerce.fileservice.service;

import org.springframework.web.multipart.MultipartFile;
import uz.ecommerce.commons.model.response.FileResponse;

public interface FileService {
    FileResponse upload(MultipartFile file);
    FileResponse getFileByName(String name);
    byte[] openFileByName(String name);
    void delete(int id);
}