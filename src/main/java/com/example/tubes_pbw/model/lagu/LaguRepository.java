package com.example.tubes_pbw.model.lagu;

import java.util.Optional;

public interface LaguRepository {
    Optional<Lagu> findByIdLagu(int idLagu);
    Iterable<Lagu> findByIdAlbum(int idAlbum);
    Iterable<Lagu> findByNamaLagu(String namaLagu);
    int save(int idAlbum, String namaLagu, int duration, int idImage);
}
