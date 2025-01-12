package com.example.tubes_pbw.model.setlistHistory;

import java.sql.Timestamp;
import java.util.List;

public interface SetlistHistoryRepository {
    Iterable<SetlistHistory> findAllByOrderByTanggalDiubahDesc();

    Iterable<SetlistHistoryPengguna> findSetlistHistoryWithPengguna(int idSetlist);

    List<LaguNowBef> findLaguBefAfter(int idSetlist, Timestamp date);

    List<SetlistNowBef> findSetlistNowBef(int idSetlist, Timestamp date);

    List<SetlistHistory> findSetlistHistory(int idSetlist, Timestamp date);
}
