package com.example.tubes_pbw.model.artis;

import java.util.List;
import java.util.Optional;

public interface ArtisRepository {
    Optional<Artis> findByNamaArtis(String namaArtis);
    Iterable<Artis> findByFilterNamaArtis(String namaArtis);
    Iterable<Artis> findByFilterNamaArtisWithOffset(String namaArtis,int offset);
    long countByFilterNamaArtis(String namaArtis);
    Iterable<Artis> findByIdArtis(int idArtis);
    int save(String namaArtis, String urlGambarArtis);
    void deleteById(int idArtis);
    List<Artis> findTopArtisBySetlistLagu();
}
