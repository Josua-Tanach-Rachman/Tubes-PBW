package com.example.tubes_pbw.model.lagu;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcLaguRepository implements LaguRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Lagu> findByIdLagu(int idLagu) {
        String sql = "SELECT * FROM lagu WHERE idlagu = ?";
        List<Lagu> results = jdbcTemplate.query(sql, this::mapRowToLagu, idLagu);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public Iterable<Lagu> findByIdAlbum(int idAlbum) {
        String sql = "SELECT * FROM lagu WHERE idalbum = ?";
        return jdbcTemplate.query(sql, this::mapRowToLagu, idAlbum);
    }

    @Override
    public Iterable<Lagu> findByNamaLagu(String namaLagu) {
        String sql = "SELECT * FROM lagu WHERE namalagu ILIKE ?";
        return jdbcTemplate.query(sql, this::mapRowToLagu, "%" + namaLagu + "%");
    }

    @Override
    public int save(int idAlbum, String namaLagu, int duration, String urlGambarLagu) {
        String sql = "INSERT INTO lagu (idalbum, namalagu, duration, urlgambarlagu) VALUES (?, ?, ?, ?) RETURNING idlagu";
        int idLagu = jdbcTemplate.queryForObject(sql,Integer.class, idAlbum, namaLagu, duration, urlGambarLagu);
        return idLagu;
    }

    private Lagu mapRowToLagu(ResultSet resultSet, int rowNum) throws SQLException {
        return new Lagu(
            resultSet.getInt("idLagu"),
            resultSet.getInt("idAlbum"),
            resultSet.getString("namaLagu"),
            resultSet.getInt("duration"),
            resultSet.getString("urlGambarLagu")
        );
    }
}
