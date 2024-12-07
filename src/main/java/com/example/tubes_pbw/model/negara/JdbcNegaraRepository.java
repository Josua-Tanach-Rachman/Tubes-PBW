package com.example.tubes_pbw.model.negara;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcNegaraRepository implements NegaraRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Negara> findByNamaNegara(String namaNegara) {
        String sql = "SELECT * FROM negara WHERE namanegara = ?";
        List<Negara> res = jdbcTemplate.query(sql,this::mapRowToNegara,namaNegara);
        return res.size() == 0 ? Optional.empty() : Optional.of(res.get(0));
    }

    @Override
    public Iterable<Negara> findByFilterNamaNegara(String namaNegara){
        String sql = "SELECT * FROM negara WHERE namanegara ILIKE ?";
        return jdbcTemplate.query(sql, this::mapRowToNegara, "%" + namaNegara + "%");
    }

    private Negara mapRowToNegara(ResultSet resultSet, int rowNum) throws SQLException {
        return new Negara(
            resultSet.getInt("idNegara"),
            resultSet.getString("namaNegara")
        );
    }
}
