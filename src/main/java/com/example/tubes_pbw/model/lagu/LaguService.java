package com.example.tubes_pbw.model.lagu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LaguService {

    @Autowired
    private LaguRepository laguRepository;

    public Optional<Lagu> findByIdLagu(int idLagu) {
        return laguRepository.findByIdLagu(idLagu);
    }

    public Iterable<Lagu> findByIdAlbum(int idAlbum) {
        return laguRepository.findByIdAlbum(idAlbum);
    }

    public Iterable<Lagu> findByNamaLagu(String namaLagu) {
        return laguRepository.findByNamaLagu(namaLagu);
    }

    public int save(int idAlbum, String namaLagu, int duration, int idImage) {
        return laguRepository.save(idAlbum,namaLagu,duration, idImage);
    }
}
