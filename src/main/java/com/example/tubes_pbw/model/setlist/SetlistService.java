package com.example.tubes_pbw.model.setlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class SetlistService {

    @Autowired
    private SetlistRepository setlistRepository;

    public Optional<Setlist> findByIdSetlist(int idSetlist) {
        return setlistRepository.findByIdSetlist(idSetlist);
    }

    public Iterable<Setlist> findByNamaSetlist(String namaSetlist) {
        return setlistRepository.findByNamaSetlist(namaSetlist);
    }

    public Iterable<Setlist> findByLokasi(int idLokasi) {
        return setlistRepository.findByLokasi(idLokasi);
    }

    public void save(String namaSetlist, LocalDate tanggal, int idLokasi, String urlBukti, int idImage) {
        setlistRepository.save(namaSetlist,tanggal,idLokasi,urlBukti, idImage);
    }
}
