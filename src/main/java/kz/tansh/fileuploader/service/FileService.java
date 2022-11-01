package kz.tansh.fileuploader.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

  ResponseEntity<String> checkFileOk(MultipartFile multipartFile) throws IOException;

  String save(MultipartFile multipartFile) throws IOException;

}
