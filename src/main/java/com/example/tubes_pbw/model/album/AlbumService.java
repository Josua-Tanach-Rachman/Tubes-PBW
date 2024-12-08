package com.example.tubes_pbw.model.album;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void save(String namaAlbum, String releaseDate, int idImage) {
        albumRepository.save(namaAlbum, releaseDate, idImage);
    }

    public void deleteById(int idAlbum) {
        albumRepository.deleteById(idAlbum);
    }
}
