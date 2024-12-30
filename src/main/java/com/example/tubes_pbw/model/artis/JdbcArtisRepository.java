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
        String sql = "SELECT * FROM artis WHERE namaartis = ? ORDER BY namaartis";
        List<Artis> res = jdbcTemplate.query(sql, this::mapRowToArtis, namaArtis);
        return res.isEmpty() ? Optional.empty() : Optional.of(res.get(0));
    }

    @Override
    public Iterable<Artis> findByFilterNamaArtis(String namaArtis) {
        String sql = "SELECT * FROM artis WHERE namaartis ILIKE ? ORDER BY namaartis";
        return jdbcTemplate.query(sql, this::mapRowToArtis, "%" + namaArtis + "%");
    }

    @Override
    public long countByFilterNamaArtis(String namaArtis) {
        String sql = "SELECT COUNT(idartis) FROM artis WHERE namaartis ILIKE ?";
        return jdbcTemplate.queryForObject(sql, Long.class, "%" + namaArtis + "%");
    }

    @Override
    public Iterable<Artis> findByFilterNamaArtisWithOffset(String namaArtis, int limit, int offset) {
        String sql = "SELECT * FROM artis WHERE namaartis ILIKE ? ORDER BY namaartis LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, this::mapRowToArtis, "%" + namaArtis + "%", limit, offset);
    }

    @Override
    public Iterable<ArtisSetlistCountDTO> findByFilterNamaArtisWithOffsetReturnWithCount(String namaArtis,int limit, int offset){
        String sql = "SELECT a.idArtis, a.namaArtis, COUNT(s.idSetlist) AS jumlahSetlist " +
                     "FROM artis a " +
                     "LEFT JOIN setlist s ON a.idArtis = s.idArtis " +
                     "WHERE a.namaArtis ILIKE ? " +
                     "GROUP BY a.idArtis, a.namaArtis " +
                     "ORDER BY jumlahSetlist DESC " +
                     "LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, this::mapRowToArtisSetlistCountDTO, "%" + namaArtis + "%", limit, offset);
    }

    @Override
    public long maxSetlistCountForArtis() {
        String sql = "SELECT MAX(jumlahSetlist) " +
                    "FROM ( " +
                    "SELECT a.namaArtis, a.idArtis, COUNT(s.idSetlist) AS jumlahSetlist " +
                     "FROM artis a " +
                     "LEFT JOIN setlist s ON a.idArtis = s.idArtis " +
                     "GROUP BY a.idArtis, a.namaArtis " +
                     "ORDER BY jumlahSetlist DESC " +
                    ") AS artistSetlists";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }

    @Override
    public Iterable<Artis> findByIdArtis(int idArtis) {
        String sql = "SELECT * FROM artis WHERE idartis = ? ORDER BY namaartis";
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

    private Artis mapRowToArtis(ResultSet resultSet, int rowNum) throws SQLException {
        return new Artis(
            resultSet.getInt("idArtis"),
            resultSet.getString("namaArtis"),
            resultSet.getString("urlGambarArtis")
        );
    }

    private ArtisSetlistCountDTO mapRowToArtisSetlistCountDTO(ResultSet resultSet, int rowNum) throws SQLException {
        return new ArtisSetlistCountDTO(
            resultSet.getInt("idArtis"),
            resultSet.getString("namaArtis"),
            resultSet.getInt("jumlahSetlist")
        );
    }
}
