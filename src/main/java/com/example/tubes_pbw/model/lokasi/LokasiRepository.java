package com.example.tubes_pbw.model.lokasi;

import java.util.List;
import java.util.Optional;

public interface LokasiRepository {
    Optional<Lokasi> findByNamaLokasi(String namaLokasi);
    Iterable<Lokasi> findByFilterNamaLokasi(String namaLokasi);
    Iterable<Lokasi> findByIdKota(int idKota);
    List<Lokasi> findByIdLokasi(int idLokasi);
}
