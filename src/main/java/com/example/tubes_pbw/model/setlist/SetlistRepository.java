package com.example.tubes_pbw.model.setlist;

import java.time.LocalDate;
import java.util.Optional;

public interface SetlistRepository {
    Optional<Setlist> findByIdSetlist(int idSetlist);
    Iterable<Setlist> findByNamaSetlist(String namaSetlist);
    Iterable<Setlist> findByLokasi(int idLokasi);
    int save(String namaSetlist, LocalDate tanggal, int idLokasi, String urlBukti);
}
