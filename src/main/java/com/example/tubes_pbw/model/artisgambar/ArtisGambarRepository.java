package com.example.tubes_pbw.model.artisgambar;

import java.util.Optional;

public interface ArtisGambarRepository {
    Optional<ArtisGambar> findByNamaArtis(String namaArtis);
    Iterable<ArtisGambar> findByFilterNamaArtis(String namaArtis);
    Iterable<ArtisGambar> findByIdArtis(int idArtis);
}
