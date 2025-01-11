package com.example.tubes_pbw.model.album;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    public List<Album> findByNamaAlbum(String namaAlbum) {
        Iterable<Album> it = albumRepository.findByNamaAlbum(namaAlbum);
        List<Album> list = new ArrayList<>();
        for(Album a: it){
            list.add(a);
        }
        return list;
    }

    public Iterable<Album> findAll() {
        return albumRepository.findAll();
    }

    public void save(String namaAlbum, Date releaseDate, int idartis, String urlGambarAlbum) {
        albumRepository.save(namaAlbum, releaseDate, idartis, urlGambarAlbum);
    }

    public void deleteById(int idAlbum) {
        albumRepository.deleteById(idAlbum);
    }

    public Iterable<Album> findByFilterNamaAlbum(String namaAlbum) {
        return albumRepository.findByFilterNamaAlbum(namaAlbum);
    }

    public Iterable<Album> findByIdArtis(int idArtis) {
        return albumRepository.findByIdArtis(idArtis);
    }
}
