package kz.tansh.fileuploader.exceptions;


public class IllegalFileException extends RuntimeException {

  public IllegalFileException() {
    super("Ошибка при загрузке файла");
  }

  public IllegalFileException(String additionalMessage) {
    super("Ошибка при загрузке файла, " + additionalMessage);
  }

}
