package com.example.tubes_pbw.model.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcImageRepository implements ImageRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private static final String INSERT_IMAGE_SQL = "INSERT INTO images (name, type, imagedata) VALUES (?, ?, ?)";
    private static final String FIND_BY_NAME_SQL = "SELECT * FROM images WHERE name = ?";

    public Image mapRowToImage(ResultSet rs, int rowNum) throws SQLException {
        return new Image(
            rs.getLong("id"), 
            rs.getString("name"), 
            rs.getString("type"), 
            rs.getBytes("imageData")
        );
    }

    public void save(Image image) {
        jdbcTemplate.update(INSERT_IMAGE_SQL, image.getName(), image.getType(), image.getImageData());
    }

    public Optional<Image> findByName(String name) {
        List<Image> results =  jdbcTemplate.query(FIND_BY_NAME_SQL, this::mapRowToImage, name);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
}

