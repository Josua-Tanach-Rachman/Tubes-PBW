package com.example.tubes_pbw.model.komentar;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcKomentarRepository implements KomentarRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Komentar> findByIdKomentar(int idKomentar) {
        String sql = "SELECT * FROM komentar WHERE idkomentar = ?";
        List<Komentar> results = jdbcTemplate.query(sql, this::mapRowToKomentar, idKomentar);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public Iterable<Komentar> findBySetlistId(int idSetlist) {
        String sql = "SELECT * FROM komentar WHERE idsetlist = ?";
        return jdbcTemplate.query(sql, this::mapRowToKomentar, idSetlist);
    }

    @Override
    public Iterable<Komentar> findByPenggunaId(int idPengguna) {
        String sql = "SELECT * FROM komentar WHERE idpengguna = ?";
        return jdbcTemplate.query(sql, this::mapRowToKomentar, idPengguna);
    }

    @Override
    public int save(String email, int idSetlist, String komentar) {
        String sql = "INSERT INTO komentar (email, idsetlist, komentar) VALUES (?, ?, ?) RETURNING idkomentar";
        int idKomentar = jdbcTemplate.queryForObject(sql,Integer.class, email, idSetlist, komentar);
        return idKomentar;
    }

    @Override
    public Iterable<KomentarPengguna> findKomentarPenggunaBySetlistId(int idSetlist) {
        String sql = "SELECT * FROM komentar k JOIN pengguna p ON k.email = p.email WHERE k.idsetlist = ? ORDER By tanggal DESC";
        return jdbcTemplate.query(sql, this::mapRowToKomentarPengguna, idSetlist);
    }

    private Komentar mapRowToKomentar(ResultSet resultSet, int rowNum) throws java.sql.SQLException {
        return new Komentar(
            resultSet.getInt("idkomentar"),
            resultSet.getString("email"),
            resultSet.getInt("idsetlist"),
            resultSet.getString("komentar"),
            resultSet.getTimestamp("tanggal")
        );
    }

    private KomentarPengguna mapRowToKomentarPengguna(ResultSet resultSet, int rowNum) throws java.sql.SQLException {
        return new KomentarPengguna(
            resultSet.getInt("idkomentar"),
            resultSet.getString("email"),
            resultSet.getString("username"),
            resultSet.getString("komentar"),
            resultSet.getTimestamp("tanggal")
        );
    }
    
}
