package com.example.tubes_pbw.model.artis;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Artis {
    @NotBlank
    int idArtis;

    @NotBlank
    String namaArtis;

    String urlGambarArtis;
}
