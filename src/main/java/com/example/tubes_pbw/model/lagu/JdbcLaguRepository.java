package com.example.tubes_pbw.model.lagu;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcLaguRepository implements LaguRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Lagu> findByIdLagu(int idLagu) {
        String sql = "SELECT * FROM lagu WHERE idlagu = ?";
        List<Lagu> results = jdbcTemplate.query(sql, this::mapRowToLagu, idLagu);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public Iterable<Lagu> findByIdAlbum(int idAlbum) {
        String sql = "SELECT * FROM lagu WHERE idalbum = ?";
        return jdbcTemplate.query(sql, this::mapRowToLagu, idAlbum);
    }

    @Override
    public Iterable<Lagu> findByNamaLagu(String namaLagu) {
        String sql = "SELECT * FROM lagu WHERE namalagu ILIKE ?";
        return jdbcTemplate.query(sql, this::mapRowToLagu, "%" + namaLagu + "%");
    }

    @Override
    public int save(int idAlbum, String namaLagu, int duration, String urlGambarLagu) {
        String sql = "INSERT INTO lagu (idalbum, namalagu, duration, urlgambarlagu) VALUES (?, ?, ?, ?) RETURNING idlagu";
        int idLagu = jdbcTemplate.queryForObject(sql,Integer.class, idAlbum, namaLagu, duration, urlGambarLagu);
        return idLagu;
    }

    @Override
    public List<Lagu> findTopSong() {
        String sql = "SELECT l.idLagu, l.namaLagu, l.duration, l.idAlbum, l.idArtis, l.urlGambarLagu, COUNT(sl.idLagu) AS play_count " +
                    "FROM lagu l " +
                    "JOIN Setlist_lagu sl ON l.idLagu = sl.idLagu " +
                    "GROUP BY l.idLagu, l.namaLagu, l.duration, l.idAlbum, l.idArtis, l.urlGambarLagu " +
                    "ORDER BY play_count DESC " +
                    "LIMIT 15";
        return jdbcTemplate.query(sql, this::mapRowToLagu);
    }

    @Override
    public List<LaguJumlahSetlist> findLaguWithLimitOffset(String namaLagu, int limit, int offset) {
        String sql = "SELECT l.idLagu, l.namaLagu, COUNT(sl.idSetlist) as jumlahSetlist\n" + //
                        "FROM Lagu l\n" + //
                        "LEFT JOIN setlistlagu sl ON l.idLagu = sl.idLagu\n" + //
                        "WHERE l.namaLagu ILIKE ?\n" + //
                        "GROUP BY l.idLagu, l.namaLagu\n" + //
                        "ORDER BY jumlahSetlist DESC \n" + //
                        "LIMIT ? OFFSET ?;\n";
        return jdbcTemplate.query(sql, this::mapRowToLaguJumlahSetlist, "%" + namaLagu + "%", limit, offset);
    }
    
    @Override
    public long countByFilterNamaLagu(String namaLagu) {
        String sql = "SELECT COUNT(idLagu) FROM Lagu WHERE namaLagu ILIKE ?";
        return jdbcTemplate.queryForObject(sql, Long.class, "%" + namaLagu + "%");
    }

    @Override
    public long maxSetlistCountForEachLagu() {
        String sql = "SELECT MAX(jumlahSetlist) " +
                    "FROM ( " +
                        "SELECT l.idLagu, l.namaLagu, COUNT(sl.idSetlist) as jumlahSetlist\n" + //
                        "FROM Lagu l\n" + //
                        "LEFT JOIN Setlistlagu sl ON l.idLagu = sl.idLagu\n" + //
                        "GROUP BY l.idLagu, l.namaLagu\n" +
                        "ORDER BY jumlahSetlist DESC \n" + //
                    ") AS artistSetlists";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }

    @Override
    public List<LaguTanggalShow> findTanggalShow(int idLagu) {
        String sql = "SELECT s.tanggal, sh.idShow, sh.namaShow\n" + //
                        "FROM Lagu l\n" + //
                        "JOIN setlistlagu sl ON l.idLagu = sl.idLagu\n" + //
                        "JOIN setlist s ON sl.idsetlist = s.idsetlist\n" + //
                        "JOIN show sh oN s.idshow = sh.idshow\n" + //
                        "WHERE l.idLagu = ?\n" + //
                        "ORDER BY s.tanggal";
        return jdbcTemplate.query(sql, this::mapRowToLaguTanggalShow, idLagu);
    }

    @Override
    public LaguArtisAlbum findLaguArtis(int idLagu) {
        String sql = "SELECT a.idArtis, a.namaArtis, l.idLagu, l.namaLagu, l.idAlbum, al.namaAlbum\n" + //
                        "FROM Lagu l\n" + //
                        "JOIN artis a ON l.idArtis = a.idArtis\n" +
                        "JOIN album al ON l.idAlbum = al.idAlbum\n" +
                        "WHERE l.idLagu = ?\n";
        List<LaguArtisAlbum> list = jdbcTemplate.query(sql, this::mapRowToLaguArtis,idLagu);
        return list.get(0);
    }

    private Lagu mapRowToLagu(ResultSet resultSet, int rowNum) throws SQLException {
        return new Lagu(
            resultSet.getInt("idLagu"),
            resultSet.getInt("idAlbum"),
            resultSet.getString("namaLagu"),
            resultSet.getInt("duration"),
            resultSet.getInt("idArtis"),
            resultSet.getString("urlGambarLagu")
        );
    }

    private LaguJumlahSetlist mapRowToLaguJumlahSetlist(ResultSet resultSet, int rowNum) throws SQLException {
        return new LaguJumlahSetlist(
            resultSet.getInt("idLagu"),
            resultSet.getString("namaLagu"),
            resultSet.getInt("jumlahSetlist")
        );
    }

    private LaguTanggalShow mapRowToLaguTanggalShow(ResultSet resultSet, int rowNum) throws SQLException {
        return new LaguTanggalShow(
            resultSet.getTimestamp("tanggal"),
            resultSet.getInt("idShow"),
            resultSet.getString("namaShow")
        );
    }

    private LaguArtisAlbum mapRowToLaguArtis(ResultSet resultSet, int rowNum) throws SQLException {
        return new LaguArtisAlbum(
            resultSet.getInt("idArtis"),
            resultSet.getInt("idLagu"),
            resultSet.getString("namaArtis"),
            resultSet.getString("namaLagu"),
            resultSet.getInt("idAlbum"),
            resultSet.getString("namaAlbum")
        );
    }
    
}
