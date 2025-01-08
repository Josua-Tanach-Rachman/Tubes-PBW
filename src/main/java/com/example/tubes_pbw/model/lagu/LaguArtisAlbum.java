package com.example.tubes_pbw.model.lagu;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LaguArtisAlbum {
    private int idArtis;
    private int idLagu;
    private String namaArtis;
    private String namaLagu;
    private int idAlbum;
    private String namaAlbum;
}
