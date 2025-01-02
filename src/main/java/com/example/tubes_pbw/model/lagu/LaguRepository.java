package com.example.tubes_pbw.model.lagu;

import java.util.List;
import java.util.Optional;

public interface LaguRepository {
    Optional<Lagu> findByIdLagu(int idLagu);
    Iterable<Lagu> findByIdAlbum(int idAlbum);
    Iterable<Lagu> findByNamaLagu(String namaLagu);
    int save(int idAlbum, String namaLagu, int duration, String urlGambarLagu);
    List<Lagu> findTopSong();

    Iterable<LaguJumlahSetlist> findLaguWithLimitOffset(String namaLagu,int limit, int offset);
    long countByFilterNamaLagu(String namaLagu);
    long maxSetlistCountForEachLagu();
}
