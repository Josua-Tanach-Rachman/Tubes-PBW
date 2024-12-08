package com.example.tubes_pbw.model.image;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping("/upload")
    public String showUploadForm() {
        return "upload";
    }
    @PostMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file, @RequestParam("kategori") String kategori, Model model) {
        try {
            String message = imageService.uploadImage(file, kategori);
            model.addAttribute("message", message);
            return "upload";
        } catch (IOException e) {
            model.addAttribute("message", "Error uploading file");
            return "upload";
        }
    }

    @GetMapping("/download")
    public String showDownloadForm() {
        return "download";
    }

    @PostMapping("/download")
    public ResponseEntity<byte[]> downloadImage(@RequestParam("imageName") String imageName, Model model) {
        try {
            System.out.println("Received imageName: " + imageName);

            byte[] imageData = imageService.downloadImage(imageName);

            if (imageData == null) {
                model.addAttribute("error", "Image " + imageName + " not found.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + imageName);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(imageData);
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            model.addAttribute("error", "Unexpected error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
