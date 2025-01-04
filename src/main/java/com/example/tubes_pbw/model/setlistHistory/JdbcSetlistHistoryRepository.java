package com.example.tubes_pbw.model.setlistHistory;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcSetlistHistoryRepository implements SetlistHistoryRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Iterable<SetlistHistory> findAllByOrderByTanggalDiubahDesc() {
        String sql = "SELECT * FROM SetlistHistory ORDER BY tanggalDiubah DESC";
        return jdbcTemplate.query(sql, this::mapRowToSetlistHistory);
    }

    @Override
    public Iterable<SetlistHistoryPengguna> findSetlistHistoryWithPengguna(int idSetlist) {
        String sql = "SELECT sh.idhistory, p.email, p.username, sh.tanggalDiubah FROM SetlistHistory sh JOIN Pengguna p ON sh.email = p.email WHERE sh.idSetlist = ? ORDER BY tanggalDiubah DESC";
        return jdbcTemplate.query(sql, this::mapRowToSetlistHistoryPengguna, idSetlist);
    }

    private SetlistHistory mapRowToSetlistHistory(ResultSet resultSet, int rowNum) throws SQLException {
        return new SetlistHistory(
            resultSet.getInt("idHistory"),
            resultSet.getInt("idSetlist"),
            resultSet.getInt("idArtis"),
            resultSet.getInt("idLokasi"),
            resultSet.getString("namaSetlist"),
            resultSet.getTimestamp("tanggal"),
            resultSet.getString("urlBukti"),
            resultSet.getInt("idShow"),
            resultSet.getString("email"),
            resultSet.getString("action"),
            resultSet.getTimestamp("tanggalDiubah")
        );
    }

    private SetlistHistoryPengguna mapRowToSetlistHistoryPengguna(ResultSet resultSet, int rowNum) throws SQLException {
        return new SetlistHistoryPengguna(
            resultSet.getInt("idHistory"),             // Map idHistory
            resultSet.getString("email"),              // Map email
            resultSet.getString("username"),           // Map username
            resultSet.getTimestamp("tanggalDiubah")    // Map tanggalDiubah (Timestamp)
        );
    }
}
