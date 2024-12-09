package com.example.tubes_pbw.model.show;

import java.util.Optional;

public interface ShowRepository {
    Optional<Show> findByIdShow(int idShow);
    Iterable<Show> findByNamaShow(String namaShow);
    int save(String namaShow, int idLokasi);
}
