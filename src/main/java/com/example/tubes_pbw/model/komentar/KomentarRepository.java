package com.example.tubes_pbw.model.komentar;

import java.util.Optional;

public interface KomentarRepository {
    Optional<Komentar> findByIdKomentar(int idKomentar);
    Iterable<Komentar> findBySetlistId(int idSetlist);
    Iterable<Komentar> findByPenggunaId(int idPengguna);
    int save(String email, int idSetlist, String komentar);

    Iterable<KomentarPengguna> findKomentarPenggunaBySetlistId(int idSetlist);
}
