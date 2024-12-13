package com.example.tubes_pbw.model.show;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcShowRepository implements ShowRepository{
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Show> findByIdShow(int idShow) {
        String sql = "SELECT * FROM show WHERE idshow = ?";
        List<Show> results = jdbcTemplate.query(sql, this::mapRowToShow, idShow);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public Iterable<Show> findByNamaShow(String namaShow) {
        String sql = "SELECT * FROM show WHERE namashow ILIKE ?";
        return jdbcTemplate.query(sql, this::mapRowToShow, "%" + namaShow + "%");
    }

    @Override
    public int save(String namaShow, int idLokasi) {
        String sql = "INSERT INTO show (namashow, idlokasi) VALUES (?, ?) RETURNING idshow";
        int idSetlist = jdbcTemplate.queryForObject(sql,Integer.class, namaShow, idLokasi);
        return idSetlist;
    }

    private Show mapRowToShow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Show(
            resultSet.getInt("idShow"),
            resultSet.getString("namaShow"),
            resultSet.getInt("idLokasi"),
            resultSet.getString("urlGambarShow")
        );
    }
}
