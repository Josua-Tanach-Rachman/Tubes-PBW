package com.example.tubes_pbw.model.artis;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcArtisRepository implements ArtisRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Artis> findByNamaArtis(String namaArtis) {
        String sql = "SELECT * FROM artis WHERE namaartis = ?";
        List<Artis> res = jdbcTemplate.query(sql, this::mapRowToArtis, namaArtis);
        return res.isEmpty() ? Optional.empty() : Optional.of(res.get(0));
    }

    @Override
    public Iterable<Artis> findByFilterNamaArtis(String namaArtis) {
        String sql = "SELECT * FROM artis WHERE namaartis ILIKE ?";
        return jdbcTemplate.query(sql, this::mapRowToArtis, "%" + namaArtis + "%");
    }

    @Override
    public Iterable<Artis> findByIdArtis(int idArtis) {
        String sql = "SELECT * FROM artis WHERE idartis = ?";
        return jdbcTemplate.query(sql, this::mapRowToArtis, idArtis);
    }

    @Override
    public void save(String namaArtis) {
        String sql = "INSERT INTO artis (namaartis) VALUES (?)";
        jdbcTemplate.update(sql, namaArtis);
    }

    @Override
    public void deleteById(int idArtis) {
        String sql = "DELETE FROM artis WHERE idartis = ?";
        jdbcTemplate.update(sql, idArtis);
    }

    private Artis mapRowToArtis(ResultSet resultSet, int rowNum) throws SQLException {
        return new Artis(
            resultSet.getInt("idArtis"),
            resultSet.getString("namaArtis")
        );
    }
}
