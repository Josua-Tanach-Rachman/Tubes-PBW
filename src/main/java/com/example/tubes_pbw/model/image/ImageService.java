package com.example.tubes_pbw.model.image;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final JdbcImageRepository jdbcImageRepository;

    public String uploadImage(MultipartFile imageFile) throws IOException {
        Image imageToSave = new Image();
        imageToSave.setName(imageFile.getOriginalFilename());
        imageToSave.setType(imageFile.getContentType());
        imageToSave.setImageData(imageFile.getBytes());

        jdbcImageRepository.save(imageToSave);
        return "File uploaded successfully: " + imageFile.getOriginalFilename();
    }

    public byte[] downloadImage(String imageName) {
        Optional<Image> dbImage = jdbcImageRepository.findByName(imageName);
        return dbImage.map(Image::getImageData)
                      .orElseThrow(() -> new RuntimeException("Image not found with name: " + imageName));
    }
}
