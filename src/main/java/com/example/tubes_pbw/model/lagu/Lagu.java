package com.example.tubes_pbw.model.lagu;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Lagu {
    private int idLagu;
    
    @NotBlank
    private int idAlbum;
    
    @NotBlank
    private String namaLagu;
    
    private int duration;
    private int idArtis;
    private String urlGambarLagu;
}
