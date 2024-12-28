package com.example.tubes_pbw.model.artis;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jdbc.repository.query.Query;
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
    public int save(String namaArtis, String urlGambarArtis) {
        String sql = "INSERT INTO artis (namaartis, urlGambarArtis) VALUES (?,?) RETURNING idartis";
        int idArtis = jdbcTemplate.queryForObject(sql, Integer.class, namaArtis, urlGambarArtis);
        return idArtis;
    }

    @Override
    public void deleteById(int idArtis) {
        String sql = "DELETE FROM artis WHERE idartis = ?";
        jdbcTemplate.update(sql, idArtis);
    }

    @Override
    public List<Artis> findTopArtisBySetlistLagu() {
        String sql = "SELECT a.idartis, a.namaartis, a.urlgambarartis FROM artis a JOIN lagu l ON a.idartis = l.idartis JOIN setlist_lagu sl ON l.idlagu = sl.idlagu GROUP BY a.idartis, a.namaartis, a.urlgambarartis ORDER BY COUNT(sl.idlagu) DESC LIMIT 15";
        return jdbcTemplate.query(sql, this::mapRowToArtis);
    }


    private Artis mapRowToArtis(ResultSet resultSet, int rowNum) throws SQLException {
        return new Artis(
            resultSet.getInt("idArtis"),
            resultSet.getString("namaArtis"),
            resultSet.getString("urlGambarArtis")
        );
    }
}