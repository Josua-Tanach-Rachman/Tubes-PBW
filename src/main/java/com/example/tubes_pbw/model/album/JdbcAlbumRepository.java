package com.example.tubes_pbw.model.album;

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
    public void save(String namaAlbum, String releaseDate) {
        String sql = "INSERT INTO album (namaalbum, release_date) VALUES (?, ?)";
        jdbcTemplate.update(sql, namaAlbum, releaseDate);
    }

    @Override
    public void deleteById(int idAlbum) {
        String sql = "DELETE FROM album WHERE idalbum = ?";
        jdbcTemplate.update(sql, idAlbum);
    }

    private Album mapRowToAlbum(ResultSet rs, int rowNum) throws SQLException {
        return new Album(
                rs.getInt("idAlbum"),
                rs.getString("namaAlbum"),
                rs.getDate("release_date").toLocalDate()
        );
    }
}
