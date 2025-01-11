package com.example.tubes_pbw.model.kota;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KotaService {
    @Autowired
    KotaRepository repo;

    public Optional<Kota> findByNamaKota(String namaKota) {
        return repo.findByNamaKota(namaKota);
    }

    public Iterable<Kota> findByFilterNamaKota(String namaKota) {
        return repo.findByFilterNamaKota(namaKota);
    }

    public Iterable<Kota> findByIdNegara(int idNegara) {
        return repo.findByIdNegara(idNegara);
    }

    public List<Kota> findByIdKota(int idKota) {
        return repo.findByIdKota(idKota);
    }
}
