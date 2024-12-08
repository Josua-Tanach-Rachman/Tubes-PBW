package com.example.tubes_pbw.model.album;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Album {
    private int idAlbum;
    private String namaAlbum;
    private LocalDate releaseDate;
    private int idImage;
}
