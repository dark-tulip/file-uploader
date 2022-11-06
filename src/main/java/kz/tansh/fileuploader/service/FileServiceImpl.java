package kz.tansh.fileuploader.service;

import ch.qos.logback.core.util.FileSize;
import kz.tansh.fileuploader.exceptions.IllegalFileException;
import kz.tansh.fileuploader.exceptions.IllegalFileSizeException;
import org.apache.tomcat.util.digester.RulesBase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

import static kz.tansh.fileuploader.service.FileConfigs.*;

@Service
public class FileServiceImpl implements FileService {


  @Override
  public ResponseEntity<String> checkFileOk(MultipartFile file) {

    String message;
    HttpStatus httpStatus;

    // Todo нужно перевести в отдельные exceptions
    try {
      validateFile(file);
      message = "Файл " + file.getOriginalFilename() + " успешно загружен";
      httpStatus = HttpStatus.OK;
    } catch (IllegalFileException e) {
      System.out.println("FILE UPLOAD EXCEPTION: " + e.getMessage());
      message = e.getMessage();
      httpStatus = HttpStatus.BAD_REQUEST;
    }

    System.out.println(" :: File name: " + file.getOriginalFilename());
    System.out.println(" :: File extension: " + FileConfigs.getFileExtension(file.getOriginalFilename()));
    System.out.println(" :: File size is: " + file.getSize() / 1024 + " KB");
    System.out.println(" :: httpStatus: " + httpStatus);
    System.out.println(" :: message: " + message);
    System.out.println(" =================================================================== ");
    return new ResponseEntity<>(message, httpStatus);
  }

  @Override
  public String save(MultipartFile file) throws IOException {

    // normalize the file path
    String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

    // set save path
    Path path = Paths.get(FileConfigs.UPLOAD_DIR + fileName);

    // copy file to local sir
    Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

    return "successfully uploaded " + file.getOriginalFilename();
  }
}
