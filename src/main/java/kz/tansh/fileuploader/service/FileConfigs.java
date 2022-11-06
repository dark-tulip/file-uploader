package kz.tansh.fileuploader.service;

import ch.qos.logback.core.util.FileSize;
import kz.tansh.fileuploader.exceptions.IllegalFileException;
import kz.tansh.fileuploader.exceptions.IllegalFileSizeException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Optional;

public class FileConfigs {

  public static final String UPLOAD_DIR = "/Users/tansh/Downloads/file-uploader/src/main/resources/media/";

  public static final String[] ALLOWED_FILE_EXTENSIONS = new String[]{"png", "jpeg", "docx", "pptx", "xlsx"};

  /***
   * the characters after the last dot ‘.' known as the file extension.
   */
  public static String getFileExtension(String filename) {
    return Optional.of(filename)
      .filter(f -> f.contains("."))
      .map(f -> f.substring(filename.lastIndexOf(".") + 1))
      .orElse("");
  }

  public static boolean checkFileExtensionNotAllowed(String filename) {
    String fileExtension = getFileExtension(filename);

    boolean isEmpty = fileExtension.isEmpty();
    boolean isNotAllowed = !Arrays.asList(ALLOWED_FILE_EXTENSIONS).contains(fileExtension);

    System.out.println(" :: checkFileExtensionNotAllowed: isNotAllowed: " + isNotAllowed);
    System.out.println(" :: checkFileExtensionNotAllowed: isEmpty: " + isEmpty);
    return isEmpty || isNotAllowed;
  }
  static void validateFile(MultipartFile file) {

    if (file.isEmpty()) {
      throw new IllegalFileException("Ошибка, загружен пустой файл");
    }

    if (file.getSize() > FileSize.MB_COEFFICIENT * 2) {
      throw new IllegalFileSizeException();
    }

    String filename = file.getOriginalFilename();
    if (FileConfigs.checkFileExtensionNotAllowed(filename)) {
      throw new IllegalFileException("Недопустимый формат загружаемого файла, " + FileConfigs.getFileExtension(filename));
    }
  }

}
