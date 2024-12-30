package com.example.tubes_pbw.model.setlist;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcSetlistRepository implements SetlistRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Setlist> findByIdSetlist(int idSetlist) {
        String sql = "SELECT * FROM setlist WHERE idsetlist = ?";
        List<Setlist> results = jdbcTemplate.query(sql, this::mapRowToSetlist, idSetlist);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public Iterable<Setlist> findByNamaSetlist(String namaSetlist) {
        String sql = "SELECT * FROM setlist WHERE namasetlist ILIKE ?";
        return jdbcTemplate.query(sql, this::mapRowToSetlist, "%" + namaSetlist + "%");
    }

    @Override
    public Iterable<Setlist> findByShow(int idShow) {
        String sql = "SELECT * FROM setlist WHERE idshow = ?";
        return jdbcTemplate.query(sql, this::mapRowToSetlist, idShow);
    }

    @Override
    public int save(String namaSetlist, Timestamp tanggal, int idArtis, int idLokasi, String urlBukti) {
        String sql = "INSERT INTO setlist (namasetlist, tanggal, idartis, idlokasi, urlbukti) " +
                    "VALUES (?, ?, ?, ?, ?) RETURNING idsetlist";
        
        int idSetlist = jdbcTemplate.queryForObject(sql, Integer.class, namaSetlist, tanggal, idArtis, idLokasi, urlBukti);
        
        return idSetlist;
    }


    private Setlist mapRowToSetlist(ResultSet resultSet, int rowNum) throws SQLException {
        return new Setlist(
            resultSet.getInt("idSetlist"),
            resultSet.getString("namaSetlist"), 
            resultSet.getInt("idArtis"),    
            resultSet.getInt("idLokasi"),     
            resultSet.getTimestamp("tanggal").toLocalDateTime(), 
            resultSet.getString("urlBukti"),   
            resultSet.getInt("idShow")         
        );
    }
    
}
