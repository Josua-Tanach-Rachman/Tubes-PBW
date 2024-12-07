package com.example.tubes_pbw.model.setlist;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcSetlistRepository implements SetlistRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Setlist> findByIdSetlist(int idSetlist) {
        String sql = "SELECT * FROM setlist WHERE idsetlist = ?";
        List<Setlist> results = jdbcTemplate.query(sql, this::mapRowToSetlist, idSetlist);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public Iterable<Setlist> findByNamaSetlist(String namaSetlist) {
        String sql = "SELECT * FROM setlist WHERE namasetlist ILIKE ?";
        return jdbcTemplate.query(sql, this::mapRowToSetlist, "%" + namaSetlist + "%");
    }

    @Override
    public Iterable<Setlist> findByLokasi(int idLokasi) {
        String sql = "SELECT * FROM setlist WHERE idlokasi = ?";
        return jdbcTemplate.query(sql, this::mapRowToSetlist, idLokasi);
    }

    @Override
    public int save(String namaSetlist, LocalDate tanggal, int idLokasi, String urlBukti) {
        String sql = "INSERT INTO setlist (namasetlist, tanggal, description, idlokasi, urlbukti) VALUES (?, ?, ?, ?, ?) RETURNING idsetlist";
        int idSetlist = jdbcTemplate.queryForObject(sql,Integer.class, namaSetlist, tanggal, idLokasi, urlBukti);
        return idSetlist;
    }

    private Setlist mapRowToSetlist(ResultSet resultSet, int rowNum) throws SQLException {
        return new Setlist(
            resultSet.getInt("idsetlist"),
            resultSet.getString("namasetlist"),
            resultSet.getTimestamp("tanggal").toLocalDateTime(),
            resultSet.getInt("idlokasi"),
            resultSet.getString("urlbukti")
        );
    }
}
