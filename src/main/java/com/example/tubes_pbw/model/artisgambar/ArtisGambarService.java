package com.example.tubes_pbw.model.artisgambar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
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

    public void encodeImageDataToBase64(List<ArtisGambar> listArtis) {
        for (ArtisGambar artis : listArtis) {
            if (artis.getImageData() != null) {
                String base64Image = Base64.getEncoder().encodeToString(artis.getImageData());
                artis.setImageDataBase64(base64Image);
            }
        }
    }
}
