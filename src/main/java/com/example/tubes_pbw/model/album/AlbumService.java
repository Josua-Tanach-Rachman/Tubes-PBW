package com.example.tubes_pbw.model.album;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    public Optional<Album> findById(int idAlbum) {
        return albumRepository.findById(idAlbum);
    }

    public Iterable<Album> findAll() {
        return albumRepository.findAll();
    }

    public void save(String namaAlbum, String releaseDate) {
        albumRepository.save(namaAlbum, releaseDate);
    }

    public void deleteById(int idAlbum) {
        albumRepository.deleteById(idAlbum);
    }
}
