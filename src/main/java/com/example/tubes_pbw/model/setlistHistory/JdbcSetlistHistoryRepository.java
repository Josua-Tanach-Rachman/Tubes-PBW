package com.example.tubes_pbw.model.setlistHistory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

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
        String sql = "SELECT DISTINCT ON (sh.idsetlist, sh.tanggalDiubah) \n" + //
                        "\tsh.idhistory, \n" + //
                        "\tsh.idSetlist, \n" + //
                        "\tp.email, \n" + //
                        "\tp.username, \n" + //
                        "\tsh.tanggalDiubah \n" + //
                        "FROM SetlistHistory sh \n" + //
                        "JOIN Pengguna p ON sh.email = p.email \n" + //
                        "WHERE sh.idSetlist = ? \n" + //
                        "ORDER BY tanggalDiubah DESC";
        return jdbcTemplate.query(sql, this::mapRowToSetlistHistoryPengguna, idSetlist);
    }

    @Override
    public List<LaguNowBef> findLaguBefAfter(int idSetlist, Timestamp date){
        String sql = "SELECT idLagu, idLaguBef, tracknumber, action\n" + //
                        "FROM setlist_laguhistory \n" + //
                        "where idsetlist = ? AND tanggalDiubah = ?\n" + //
                        "order by tracknumber";
        return jdbcTemplate.query(sql, this::mapRowToLaguNowBef, idSetlist, date);
    }

    @Override
    public List<SetlistNowBef> findSetlistNowBef(int idSetlist, Timestamp date) {
        String sql = "SELECT idShow, idShowBef, idLokasi, idLokasiBef, tanggal, tanggalBef\n" + //
                        "FROM setlistHistory\n" + //
                        "WHERE idsetlist = ? AND tanggalDiubah = ?";
        return jdbcTemplate.query(sql, this::mapRowToSetlistNowBef, idSetlist, date);
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
            resultSet.getInt("idSetlist"),
            resultSet.getString("email"),              // Map email
            resultSet.getString("username"),           // Map username
            resultSet.getTimestamp("tanggalDiubah")    // Map tanggalDiubah (Timestamp)
        );
    }

    private LaguNowBef mapRowToLaguNowBef(ResultSet resultSet, int rowNum) throws SQLException {
        return new LaguNowBef(
            resultSet.getInt("idLagu"),             // Map idHistory
            resultSet.getInt("idLaguBef"),
            resultSet.getInt("tracknumber"),              // Map email
            resultSet.getString("action")
        );
    }

    private SetlistNowBef mapRowToSetlistNowBef(ResultSet resultSet, int rowNum) throws SQLException {
        return new SetlistNowBef(
            resultSet.getInt("idShow"),             // Map idHistory
            resultSet.getInt("idShowBef"),
            resultSet.getInt("idLokasi"),             // Map idHistory
            resultSet.getInt("idLokasiBef"),
            resultSet.getTimestamp("tanggal"),
            resultSet.getTimestamp("tanggalBef")
        );
    }
}
