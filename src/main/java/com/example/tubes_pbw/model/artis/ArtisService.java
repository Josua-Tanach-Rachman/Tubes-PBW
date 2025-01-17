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

    public Iterable<Artis> findByFilterNamaArtisWithOffset(String namaArtis,int limit, int offset) {
        return artisRepository.findByFilterNamaArtisWithOffset(namaArtis,limit,offset);
    }

    public Iterable<ArtisSetlistCountDTO> findByFilterNamaArtisWithOffsetReturnWithCount(String namaArtis,int limit, int offset){
        return artisRepository.findByFilterNamaArtisWithOffsetReturnWithCount(namaArtis, limit, offset);
    }

    public long maxSetlistCountForArtis(){
        return artisRepository.maxSetlistCountForArtis();
    }

    public long countByFilterNamaArtis(String namaArtis){
        return artisRepository.countByFilterNamaArtis(namaArtis);
    }

    public List<Artis> findByIdArtis(int idArtis) {
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
