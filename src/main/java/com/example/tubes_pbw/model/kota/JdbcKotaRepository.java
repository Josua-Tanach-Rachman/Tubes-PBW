package com.example.tubes_pbw.model.kota;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcKotaRepository implements KotaRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Kota> findByNamaKota(String namaKota) {
        String sql = "SELECT * FROM kota WHERE namakota = ?";
        List<Kota> res = jdbcTemplate.query(sql, this::mapRowToKota, namaKota);
        return res.isEmpty() ? Optional.empty() : Optional.of(res.get(0));
    }

    @Override
    public Iterable<Kota> findByFilterNamaKota(String namaKota) {
        String sql = "SELECT * FROM kota WHERE namakota ILIKE ?";
        return jdbcTemplate.query(sql, this::mapRowToKota, "%" + namaKota + "%");
    }

    @Override
    public Iterable<Kota> findByIdNegara(int idNegara) {
        String sql = "SELECT * FROM kota WHERE idNegara = ?";
        return jdbcTemplate.query(sql, this::mapRowToKota, idNegara);
    }

    @Override
    public List<Kota> findByIdKota(int idKota){
        String sql = "SELECT * FROM kota WHERE idKota = ?";
        return jdbcTemplate.query(sql, this::mapRowToKota, idKota);
    }

    private Kota mapRowToKota(ResultSet resultSet, int rowNum) throws SQLException {
        return new Kota(
            resultSet.getInt("idKota"),
            resultSet.getString("namaKota"),
            resultSet.getInt("idNegara")
        );
    }
}
