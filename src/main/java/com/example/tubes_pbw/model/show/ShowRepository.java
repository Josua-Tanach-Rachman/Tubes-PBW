package com.example.tubes_pbw.model.show;

import java.sql.Date;
import java.util.Optional;

public interface ShowRepository {
    Optional<Show> findByIdShow(int idShow);
    Iterable<Show> findByNamaShow(String namaShow);
    Iterable<Show> findByIdLokasi(int idLokasi);
    int save(String namaShow, int idLokasi, Date beginDate, Date endDate);

    Iterable<ShowJumlahPengguna> findShowByFilterNamaWithOffsetReturnWithCount(String namaShow,int limit, int offset);
    long countByFilterNamaShow(String namaShow);
    long maxSetlistCountForEachShow();
}
