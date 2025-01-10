package com.example.tubes_pbw.model.album;

import com.example.tubes_pbw.model.artis.Artis;
import com.example.tubes_pbw.model.kota.Kota;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class JdbcAlbumRepository implements AlbumRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Iterable<Album> findByNamaAlbum(String namaAlbum) {
        String sql = "SELECT * FROM album WHERE namaalbum ILIKE ?";
        return jdbcTemplate.query(sql, this::mapRowToAlbum, "%" + namaAlbum + "%");
    }

    @Override
    public Iterable<Album> findAll() {
        String sql = "SELECT * FROM album";
        return jdbcTemplate.query(sql, this::mapRowToAlbum);
    }

    @Override
    public int save(String namaAlbum, String releaseDate, String urlGambarAlbum) {
        String sql = "INSERT INTO album (namaalbum, release_date, urlGambarAlbum) VALUES (?, ?, ?)";
        int idAlbum = jdbcTemplate.queryForObject(sql,Integer.class, namaAlbum, releaseDate, urlGambarAlbum);
        return idAlbum;
    }

    @Override
    public void deleteById(int idAlbum) {
        String sql = "DELETE FROM album WHERE idalbum = ?";
        jdbcTemplate.update(sql, idAlbum);
    }

    @Override
    public Iterable<Album> findByFilterNamaAlbum(String namaAlbum) {
        String sql = "SELECT * FROM album WHERE namaAlbum ILIKE ? ORDER BY namaAlbum";
        return jdbcTemplate.query(sql, this::mapRowToAlbum, "%" + namaAlbum + "%");
    }

    @Override
    public Iterable<Album> findByIdArtis(int idArtis) {
        String sql = "SELECT * FROM album WHERE idartis = ?";
        return jdbcTemplate.query(sql, this::mapRowToAlbum, idArtis);
    }

    private Album mapRowToAlbum(ResultSet rs, int rowNum) throws SQLException {
        return new Album(
                rs.getInt("idAlbum"),
                rs.getString("namaAlbum"),
                rs.getDate("release_date").toLocalDate(),
                rs.getString("urlGambarAlbum")
        );
    }
}
