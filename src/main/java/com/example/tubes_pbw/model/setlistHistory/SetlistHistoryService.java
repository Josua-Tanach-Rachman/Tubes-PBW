package com.example.tubes_pbw.model.setlistHistory;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SetlistHistoryService {
    
    @Autowired
    JdbcSetlistHistoryRepository jdbcSetlistHistoryRepository;

    public Iterable<SetlistHistory> getAllSetlistHistory() {
        return jdbcSetlistHistoryRepository.findAllByOrderByTanggalDiubahDesc();
    }

    public Iterable<SetlistHistoryPengguna> findAllByOrderByTanggalDiubahDesc(int idSetlist){
        return jdbcSetlistHistoryRepository.findSetlistHistoryWithPengguna(idSetlist);
    }

    public List<LaguNowBef> findLaguBefAfter(int idSetlist, Timestamp date){
        return jdbcSetlistHistoryRepository.findLaguBefAfter(idSetlist, date);
    }

    public List<SetlistNowBef> findSetlistNowBef(int idSetlist, Timestamp date){
        return jdbcSetlistHistoryRepository.findSetlistNowBef(idSetlist, date);
    }
}
