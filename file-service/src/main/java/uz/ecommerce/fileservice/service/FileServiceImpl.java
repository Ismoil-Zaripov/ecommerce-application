package uz.ecommerce.fileservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.ecommerce.commons.exception.APIException;
import uz.ecommerce.commons.model.response.FileResponse;
import uz.ecommerce.fileservice.entity.FileEntity;
import uz.ecommerce.fileservice.repository.FileRepository;

import java.io.*;
import java.nio.file.Files;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Value("${file.upload-dir}")
    private String fileUploadDir;
    @Value("${file.url}")
    private String fileUrl;

    @Autowired
    private FileRepository fileRepository;

    @Override
    public FileResponse upload(MultipartFile multipartFile) {

        String path = String.format("%s/%s", fileUploadDir, multipartFile.getOriginalFilename());

        if (new File(path).exists()) {
            log.error("File already exists: {}", multipartFile.getOriginalFilename());
            throw new APIException(
                    "File already exists %s".formatted(multipartFile.getOriginalFilename()),
                    HttpStatus.BAD_REQUEST.value()
            );
        }

        try (OutputStream outputStream = new FileOutputStream(path)) {
            outputStream.write(multipartFile.getBytes());
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new APIException(
                    e.getMessage(),
                    INTERNAL_SERVER_ERROR.value()
            );
        }

        FileEntity fileEntity = FileEntity.builder()
                .name(multipartFile.getOriginalFilename())
                .size(multipartFile.getSize())
                .path(path)
                .url(fileUrl + "/" + multipartFile.getOriginalFilename())
                .build();

        fileRepository.save(fileEntity);

        return mapToResponse(fileEntity);
    }

    @Override
    public FileResponse getFileByName(String name) {
        return getFile(name);
    }

    @Override
    public byte[] openFileByName(String name) {
        FileResponse fileResponse = getFile(name);

        try (InputStream inputStream = new FileInputStream(fileResponse.getPath())) {
            return inputStream.readAllBytes();
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new APIException(
                    e.getMessage(),
                    INTERNAL_SERVER_ERROR.value()
            );
        }
    }

    @Override
    public void delete(int id) {
        FileEntity file = fileRepository.findById(id)
                .orElseThrow(() -> new APIException(
                        "File not found",
                        404
                ));

        try {
            if (Files.deleteIfExists(new File(file.getPath()).toPath())) {
                fileRepository.delete(file);
            }
        } catch (IOException e) {
            throw new APIException(
                    e.getMessage(),
                    INTERNAL_SERVER_ERROR.value()
            );
        }
    }

    private FileResponse mapToResponse(FileEntity file) {
        FileResponse fileResponse = new FileResponse();
        BeanUtils.copyProperties(file, fileResponse);
        return fileResponse;
    }

    private FileResponse getFile(String name) {
        return fileRepository.findByName(name)
                .map(this::mapToResponse)
                .orElseThrow(() -> new APIException(
                        "File not found",
                        404
                ));
    }
}
