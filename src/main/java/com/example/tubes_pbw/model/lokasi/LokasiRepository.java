package com.example.tubes_pbw.model.lokasi;

import java.util.Optional;

public interface LokasiRepository {
    Optional<Lokasi> findByNamaLokasi(String namaLokasi);
    Iterable<Lokasi> findByFilterNamaLokasi(String namaLokasi);
    Iterable<Lokasi> findByIdKota(int idKota);
}
