package com.example.tubes_pbw.model.artis;

import java.util.Optional;

public interface ArtisRepository {
    Optional<Artis> findByNamaArtis(String namaArtis);
    Iterable<Artis> findByFilterNamaArtis(String namaArtis);
    Iterable<Artis> findByIdArtis(int idArtis);
}
