package com.example.tubes_pbw.model.setlistHistory;

public interface SetlistHistoryRepository {
    Iterable<SetlistHistory> findAllByOrderByTanggalDiubahDesc();
}
