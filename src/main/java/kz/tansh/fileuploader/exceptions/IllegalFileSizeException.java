package kz.tansh.fileuploader.exceptions;


public class IllegalFileSizeException extends IllegalFileException {

  public IllegalFileSizeException() {
    super("Превышен размер загружаемого файла");
  }

  public IllegalFileSizeException(String additionalMessage) {
    super("Превышен размер загружаемого файла " + additionalMessage);
  }

}
