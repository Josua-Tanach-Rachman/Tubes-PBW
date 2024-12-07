package com.example.tubes_pbw.model.album;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class JdbcAlbumRepository implements AlbumRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Album> findById(int idAlbum) {
        String sql = "SELECT * FROM album WHERE idalbum = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> mapRowToAlbum(rs), idAlbum)
                .stream().findFirst();
    }

    @Override
    public Iterable<Album> findAll() {
        String sql = "SELECT * FROM album";
        return jdbcTemplate.query(sql, (rs, rowNum) -> mapRowToAlbum(rs));
    }

    @Override
    public void save(String namaAlbum, String releaseDate) {
        String sql = "INSERT INTO album (namaalbum, release_date) VALUES (?, ?)";
        jdbcTemplate.update(sql, namaAlbum, releaseDate);
    }

    @Override
    public void deleteById(int idAlbum) {
        String sql = "DELETE FROM album WHERE idAlbum = ?";
        jdbcTemplate.update(sql, idAlbum);
    }

    private Album mapRowToAlbum(ResultSet rs) throws SQLException {
        return new Album(
                rs.getInt("idAlbum"),
                rs.getString("namaAlbum"),
                rs.getDate("release_date").toLocalDate()
        );
    }
}
