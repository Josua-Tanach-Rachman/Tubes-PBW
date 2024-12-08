package com.example.tubes_pbw.model.artisgambar;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArtisGambar {
    @NotBlank
    int idArtis;

    @NotBlank
    String namaArtis;

    byte[] imageData;
}
