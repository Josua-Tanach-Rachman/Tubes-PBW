package com.example.tubes_pbw.model.album;

public interface AlbumRepository {
    Iterable<Album> findByNamaAlbum(String namaAlbum);
    Iterable<Album> findAll();
    void save(String namaAlbum, String releaseDate);
    void deleteById(int idAlbum);
}
