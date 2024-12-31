package com.example.tubes_pbw.model.lokasi;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcLokasiRepository implements LokasiRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Lokasi> findByNamaLokasi(String namaLokasi) {
        String sql = "SELECT * FROM lokasi WHERE namalokasi = ?";
        List<Lokasi> res = jdbcTemplate.query(sql,this::mapRowToLokasi,namaLokasi);
        return res.size() == 0 ? Optional.empty() : Optional.of(res.get(0));
    }

    @Override
    public Iterable<Lokasi> findByFilterNamaLokasi(String namaLokasi){
        String sql = "SELECT * FROM lokasi WHERE namalokasi ILIKE ?";
        return  jdbcTemplate.query(sql, this::mapRowToLokasi, "%" + namaLokasi + "%");
    }

    @Override
    public Iterable<Lokasi> findByIdKota(int idKota) {
        String sql = "SELECT * FROM lokasi WHERE idKota = ?";
        return jdbcTemplate.query(sql, this::mapRowToLokasi, idKota);
    }

    private Lokasi mapRowToLokasi(ResultSet resultSet, int rowNum) throws SQLException {
        return new Lokasi(
            resultSet.getInt("idLokasi"),
            resultSet.getString("namaLokasi"),
            resultSet.getString("alamatLokasi"),
            resultSet.getInt("idKota")
        );
    }
}
