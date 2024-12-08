package com.example.tubes_pbw.model.artisgambar;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcArtisGambarRepository implements ArtisGambarRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Optional<ArtisGambar> findByNamaArtis(String namaArtis) {
        String sql = "SELECT * FROM Artis_Gambar WHERE namaArtis = ?";
        List<ArtisGambar> res = jdbcTemplate.query(sql, this::mapRowToArtisGambar, namaArtis);
        return res.isEmpty() ? Optional.empty() : Optional.of(res.get(0));
    }

    @Override
    public Iterable<ArtisGambar> findByFilterNamaArtis(String namaArtis) {
        String sql = "SELECT * FROM Artis_Gambar WHERE namaArtis ILIKE ?";
        return jdbcTemplate.query(sql, this::mapRowToArtisGambar, "%" + namaArtis + "%");
    }

    @Override
    public Iterable<ArtisGambar> findByIdArtis(int idArtis) {
        String sql = "SELECT * FROM Artis_Gambar WHERE idArtis = ?";
        return jdbcTemplate.query(sql, this::mapRowToArtisGambar, idArtis);
    }

    private ArtisGambar mapRowToArtisGambar(ResultSet resultSet, int rowNum) throws SQLException {
        return new ArtisGambar(
            resultSet.getInt("idArtis"),
            resultSet.getString("namaArtis"),
            resultSet.getBytes("imageData")
        );
    }
}
