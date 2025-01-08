package com.example.tubes_pbw.model.komentar;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcKomentarRepository implements KomentarRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Komentar> findByIdKomentar(int idKomentar) {
        String sql = "SELECT * FROM komentar WHERE idkomentar = ?";
        List<Komentar> results = jdbcTemplate.query(sql, this::mapRowToKomentar, idKomentar);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public Iterable<Komentar> findBySetlistId(int idSetlist) {
        String sql = "SELECT * FROM komentar WHERE idsetlist = ?";
        return jdbcTemplate.query(sql, this::mapRowToKomentar, idSetlist);
    }

    @Override
    public Iterable<Komentar> findByPenggunaId(int idPengguna) {
        String sql = "SELECT * FROM komentar WHERE idpengguna = ?";
        return jdbcTemplate.query(sql, this::mapRowToKomentar, idPengguna);
    }

    @Override
    public int save(int idPengguna, int idSetlist, String komentar) {
        String sql = "INSERT INTO komentar (idpengguna, idsetlist, komentar) VALUES (?, ?, ?) RETURNING idkomentar";
        int idKomentar = jdbcTemplate.queryForObject(sql,Integer.class, idPengguna, idSetlist, komentar);
        return idKomentar;
    }

    private Komentar mapRowToKomentar(ResultSet resultSet, int rowNum) throws java.sql.SQLException {
        return new Komentar(
            resultSet.getInt("idkomentar"),
            resultSet.getString("email"),
            resultSet.getInt("idsetlist"),
            resultSet.getString("komentar"),
            resultSet.getTimestamp("tanggal")
        );
    }
}
