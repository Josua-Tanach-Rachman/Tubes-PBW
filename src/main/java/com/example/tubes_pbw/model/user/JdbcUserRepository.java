package com.example.tubes_pbw.model.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcUserRepository implements UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    public void save(User user) throws Exception{
        String sql = "INSERT INTO pengguna (username, password, nama, role) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getNama(), user.getRole());
    }

    public Optional<User> findByUsername(String username) {
        String sql = "SELECT * FROM pengguna WHERE username = ?";
        List<User> results = jdbcTemplate.query(sql, this::mapRowToUser, username);
        return results.size() == 0 ? Optional.empty() : Optional.of(results.get(0));
    }

    private User mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        return new User(
            resultSet.getString("username"),
            resultSet.getString("password"),
            resultSet.getString("nama"),
            resultSet.getString("role")
        );
    }

}
