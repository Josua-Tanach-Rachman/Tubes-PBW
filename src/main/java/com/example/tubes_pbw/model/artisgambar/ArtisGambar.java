package com.example.tubes_pbw.model.artisgambar;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArtisGambar {
    @NotBlank
    int idArtis;

    @NotBlank
    String namaArtis;

    byte[] imageData;

    String imageDataBase64;

    public ArtisGambar(@NotBlank int idArtis, @NotBlank String namaArtis, byte[] imageData) {
        this.idArtis = idArtis;
        this.namaArtis = namaArtis;
        this.imageData = imageData;
    }
}
