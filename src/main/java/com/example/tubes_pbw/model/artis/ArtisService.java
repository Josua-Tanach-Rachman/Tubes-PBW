package com.example.tubes_pbw.model.artis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtisService {
    
    @Autowired
    private ArtisRepository artisRepository;

    public Optional<Artis> findByNamaArtis(String namaArtis) {
        return artisRepository.findByNamaArtis(namaArtis);
    }

    public Iterable<Artis> findByFilterNamaArtis(String namaArtis) {
        return artisRepository.findByFilterNamaArtis(namaArtis);
    }

    public Iterable<Artis> findByFilterNamaArtisWithOffset(String namaArtis,int offset) {
        return artisRepository.findByFilterNamaArtisWithOffset(namaArtis,offset);
    }

    public long countByFilterNamaArtis(String namaArtis){
        return artisRepository.countByFilterNamaArtis(namaArtis);
    }

    public Iterable<Artis> findByIdArtis(int idArtis) {
        return artisRepository.findByIdArtis(idArtis);
    }

    public void save(String namaArtis, String urlGambarArtis) {
        artisRepository.save(namaArtis, urlGambarArtis);
    }

    public void deleteById(int idArtis) {
        artisRepository.deleteById(idArtis);
    }

    public List<Artis> findTopArtisBySetlistLagu() {
        return artisRepository.findTopArtisBySetlistLagu();
    }
}
