package kz.tansh.fileuploader.controller;

import kz.tansh.fileuploader.service.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class FileUploadController {

  FileService fileService;

  public FileUploadController(FileService fileService) {
    this.fileService = fileService;
  }

  @GetMapping("/")
  public String homepage() {
    return "index";
  }

  @PostMapping("/upload")
  public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) throws IOException {

    ResponseEntity<String> checkResult = fileService.checkFileOk(file);

    if (checkResult.getStatusCodeValue() == 200) {
      fileService.save(file);
    }

    attributes.addFlashAttribute("message", checkResult.getBody());
    attributes.addFlashAttribute("uploadStatus", checkResult.getStatusCode());

    return "redirect:/";

  }

}
