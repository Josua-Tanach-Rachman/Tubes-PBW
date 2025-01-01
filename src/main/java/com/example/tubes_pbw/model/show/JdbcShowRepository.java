package com.example.tubes_pbw.model.show;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcShowRepository implements ShowRepository{
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Show> findByIdShow(int idShow) {
        String sql = "SELECT * FROM show WHERE idshow = ?";
        List<Show> results = jdbcTemplate.query(sql, this::mapRowToShow, idShow);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public Iterable<Show> findByNamaShow(String namaShow) {
        String sql = "SELECT * FROM show WHERE namashow ILIKE ?";
        return jdbcTemplate.query(sql, this::mapRowToShow, "%" + namaShow + "%");
    }

    @Override
    public Iterable<Show> findByIdLokasi(int idLokasi) {
        String sql = "SELECT * FROM show WHERE idlokasi = ?";
        return jdbcTemplate.query(sql, this::mapRowToShow, idLokasi);
    }

    @Override
    public int save(String namaShow, int idLokasi, Date beginDate, Date endDate) {
        String sql = "INSERT INTO show (namashow, idlokasi, begindate, enddate) VALUES (?, ?, ?, ?) RETURNING idshow";
        int idShow = jdbcTemplate.queryForObject(sql, Integer.class, namaShow, idLokasi, beginDate, endDate);
        return idShow;
    }
    
    @Override
    public Iterable<ShowJumlahPengguna> findShowByFilterNamaWithOffsetReturnWithCount(String namaShow, int limit, int offset) {
        String sql = "SELECT sh.idShow, sh.namaShow, COUNT(ps.email) as jumlahPengguna\n" + //
                        "FROM Show sh\n" + //
                        "LEFT JOIN Setlist s ON s.idShow = sh.idShow\n" + //
                        "LEFT JOIN Pengguna_setlist ps ON s.idSetlist = ps.idSetlist\n" + //
                        "WHERE sh.namaShow ILIKE ?\n" + //
                        "GROUP BY sh.idShow, sh.namaShow\n" + //
                        "ORDER BY jumlahPengguna DESC \n" + //
                        "LIMIT ? OFFSET ?;\n";
        return jdbcTemplate.query(sql, this::mapRowToShowJumlahPengguna, "%" + namaShow + "%", limit, offset);
    }
    
    @Override
    public long countByFilterNamaShow(String namaShow) {
        String sql = "SELECT COUNT(idShow) FROM Show WHERE namaShow ILIKE ?";
        return jdbcTemplate.queryForObject(sql, Long.class, "%" + namaShow + "%");
    }

    @Override
    public long maxSetlistCountForEachShow() {
        String sql = "SELECT MAX(jumlahPengguna) " +
                    "FROM ( " +
                        "SELECT sh.idShow, sh.namaShow, COUNT(ps.email) as jumlahPengguna\n" + //
                        "FROM Show sh\n" + //
                        "LEFT JOIN Setlist s ON s.idShow = sh.idShow\n" + //
                        "LEFT JOIN Pengguna_setlist ps ON s.idSetlist = ps.idSetlist\n" + //
                        "GROUP BY sh.idShow, sh.namaShow\n" +
                        "ORDER BY jumlahPengguna DESC \n" + //
                    ") AS artistSetlists";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }

    private Show mapRowToShow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Show(
            resultSet.getInt("idShow"),
            resultSet.getString("namaShow"),
            resultSet.getInt("idLokasi"),
            resultSet.getDate("beginDate"),
            resultSet.getDate("endDate")
        );
    }

    private ShowJumlahPengguna mapRowToShowJumlahPengguna(ResultSet resultSet, int rowNum) throws SQLException {
        return new ShowJumlahPengguna(
            resultSet.getInt("idShow"),
            resultSet.getString("namaShow"),
            resultSet.getInt("jumlahPengguna")
        );
    }
}
