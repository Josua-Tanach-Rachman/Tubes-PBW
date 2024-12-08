package com.example.tubes_pbw.model.artisgambar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ArtisGambarService {
    
    @Autowired
    private ArtisGambarRepository artisRepository;

    public Optional<ArtisGambar> findByNamaArtis(String namaArtis) {
        return artisRepository.findByNamaArtis(namaArtis);
    }

    public Iterable<ArtisGambar> findByFilterNamaArtis(String namaArtis) {
        return artisRepository.findByFilterNamaArtis(namaArtis);
    }

    public Iterable<ArtisGambar> findByIdArtis(int idArtis) {
        return artisRepository.findByIdArtis(idArtis);
    }
}
