package com.example.tubes_pbw.model.setlistHistory;

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
}
