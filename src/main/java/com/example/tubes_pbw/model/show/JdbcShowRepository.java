package com.example.tubes_pbw.model.show;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fasterxml.classmate.AnnotationOverrides.StdImpl;

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

    @Override
    public long countByIwasThere(int idShow) {
        String sql = "SELECT COUNT(DISTINCT ps.email) AS total_users\n" + //
                        "FROM Pengguna_setlist ps\n" + //
                        "JOIN Setlist s ON ps.idSetlist = s.idSetlist\n" + //
                        "WHERE s.idShow = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, idShow);
    }

    @Override 
    public String findConcertCity(int idShow){
        String sql = "SELECT k.namaKota\n" + //
                        "FROM Show s\n" + //
                        "JOIN Lokasi l ON s.idLokasi = l.idLokasi\n" + //
                        "JOIN Kota k ON l.idKota = k.idKota\n" + //
                        "WHERE s.idShow = ?";
        return jdbcTemplate.queryForObject(sql, String.class, idShow);
    }

    @Override
    public String findConcertAddress(int idShow){
        String sql = "SELECT k.namaKota\n" + //
                        "FROM Show s\n" + //
                        "JOIN Lokasi l ON s.idLokasi = l.idLokasi\n" + //
                        "JOIN Kota k ON l.idKota = k.idKota\n" + //
                        "WHERE s.idShow = ?";
        return jdbcTemplate.queryForObject(sql, String.class, idShow);
    }

    @Override 
    public String findAristOnConcert (int idShow){
        String sql = "SELECT a.namaArtis\n" + //
                        "FROM Show s\n" + //
                        "JOIN Setlist st ON s.idShow = st.idShow\n" + //
                        "JOIN Artis a ON st.idArtis = a.idArtis\n" + //
                        "WHERE s.idShow = ?";
        return jdbcTemplate.queryForObject(sql, String.class, idShow);
    }

    private Show mapRowToShow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Show(
            resultSet.getInt("idShow"),
            resultSet.getString("namaShow"),
            resultSet.getInt("idLokasi"),
            resultSet.getDate("beginDate"),
            resultSet.getDate("endDate"),
            resultSet.getString("urlGambarShow")
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
