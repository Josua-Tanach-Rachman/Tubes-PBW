package com.example.tubes_pbw.model.setlistHistory;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;


public class JdbcSetlistHistoryRepository implements SetlistHistoryRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Iterable<SetlistHistory> findAllByOrderByTanggalDiubahDesc() {
        String sql = "SELECT * FROM SetlistHistory ORDER BY tanggalDiubah DESC";
        return jdbcTemplate.query(sql, this::mapRowToSetlistHistory);
    }

    private SetlistHistory mapRowToSetlistHistory(ResultSet resultSet, int rowNum) throws SQLException {
        return new SetlistHistory(
            resultSet.getInt("idHistory"),         
            resultSet.getInt("idSetlist"),         
            resultSet.getString("namaSetlist"),     
            resultSet.getTimestamp("tanggal").toLocalDateTime(),      
            resultSet.getString("urlBukti"),        
            resultSet.getInt("idShow"),             
            resultSet.getString("action"),          
            resultSet.getTimestamp("tanggalDiubah").toLocalDateTime()
        );
    }
}
