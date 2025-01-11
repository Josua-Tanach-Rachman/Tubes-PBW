package com.example.tubes_pbw.model.album;

import java.sql.Date;

public interface AlbumRepository {
    Iterable<Album> findByNamaAlbum(String namaAlbum);
    Iterable<Album> findAll();
    int save(String namaAlbum, Date releaseDate, int idartis, String urlGambarAlbum);
    void deleteById(int idAlbum);
    Iterable<Album> findByFilterNamaAlbum(String namaAlbum);
    Iterable<Album> findByIdArtis(int idArtis);
}
