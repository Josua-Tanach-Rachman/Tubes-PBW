package com.example.tubes_pbw.model.kota;

import java.util.List;
import java.util.Optional;

public interface KotaRepository {
    Optional<Kota> findByNamaKota(String namaKota);
    Iterable<Kota> findByFilterNamaKota(String namaKota);
    Iterable<Kota> findByIdNegara(int idNegara);
    List<Kota> findByIdKota(int idKota);
}
