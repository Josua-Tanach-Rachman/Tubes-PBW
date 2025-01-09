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

    @Override
    public int save(User user) throws Exception{
        String sql = "INSERT INTO pengguna (username, password, nama, email) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql ,user.getUsername(),user.getPassword(),user.getNama(),user.getEmail());
        return 0;
    }

    @Override
    public int updateUser(int idPengguna, String nama, String username, String password, String email) {
        String sql = "UPDATE Pengguna SET nama = ?, username = ?, password = ?, email = ? WHERE idPengguna = ?";
        return jdbcTemplate.update(sql, nama, username, password, email, idPengguna);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        String sql = "SELECT * FROM pengguna WHERE username = ?";
        List<User> results = jdbcTemplate.query(sql, this::mapRowToUser, username);
        return results.size() == 0 ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public Optional<PenggunaSetlist> findInSetlist(String email, int idSetlist){
        String sql = "SELECT * FROM pengguna_setlist WHERE email = ? AND idsetlist = ?";
        List<PenggunaSetlist> results = jdbcTemplate.query(sql, this::mapRowToPenggunaSetlist, email, idSetlist);
        return results.size() == 0 ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public void addToPenggunaSetlist(String email, int idSetlist) {
        String sql = "INSERT INTO Pengguna_setlist (email, idSetlist) VALUES\r\n" + //
                        "(?, ?)";
        jdbcTemplate.update(sql, email, idSetlist);
    }

    @Override
    public void removeFromPenggunaSetlist(String email, int idSetlist) {
        String sql = "DELETE FROM Pengguna_setlist WHERE email = ? AND idSetlist = ?";
        jdbcTemplate.update(sql, email, idSetlist);
    }

    private User mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        return new User(
            resultSet.getString("username"),
            resultSet.getString("password"),
            resultSet.getString("nama"),
            resultSet.getString("role"),
            resultSet.getString("email")
        );
    }

    private PenggunaSetlist mapRowToPenggunaSetlist(ResultSet resultSet, int rowNum) throws SQLException {
        return new PenggunaSetlist(
            resultSet.getString("email"),
            resultSet.getInt("idSetlist")
        );
    }

}
