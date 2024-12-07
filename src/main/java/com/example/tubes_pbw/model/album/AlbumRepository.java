package com.example.tubes_pbw.model.album;

import java.util.Optional;

public interface AlbumRepository {
    Optional<Album> findById(int idAlbum);
    Iterable<Album> findAll();
    void save(String namaAlbum, String releaseDate);
    void deleteById(int idAlbum);
}
