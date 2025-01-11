package com.example.tubes_pbw.model.lokasi;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LokasiService {
    @Autowired
    LokasiRepository repo;

    public Optional<Lokasi> findByNamaLokasi(String namaLokasi) {
        return repo.findByNamaLokasi(namaLokasi);
    }

    public Iterable<Lokasi> findByFilterNamaLokasi(String namaLokasi){
        return repo.findByFilterNamaLokasi(namaLokasi);
    }

    public Iterable<Lokasi> findByIdKota(int idKota) {
        return repo.findByIdKota(idKota);
    }

    public List<Lokasi> findByIdLokasi(int idLokasi){
        return repo.findByIdLokasi(idLokasi);
    }
}

