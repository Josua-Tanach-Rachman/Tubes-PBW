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
    public int save(String namaSetlist, Timestamp tanggal, int idArtis, int idLokasi, String urlBukti, int idShow) {
        String sql = "INSERT INTO setlist (namasetlist, tanggal, idartis, idlokasi, urlbukti, idShow) " +
                    "VALUES (?, ?, ?, ?, ?, ?) RETURNING idsetlist";
        
        int idSetlist = jdbcTemplate.queryForObject(sql, Integer.class, namaSetlist, tanggal, idArtis, idLokasi, urlBukti,idShow);
        
        return idSetlist;
    }

    @Override
    public List<ArtistSetlistLokasiDate> findArtistSetlistLokasiDateByIdArtis(int idArtis) {
        String sql = """
            SELECT 
                s.idSetlist,
                a.idArtis,
                l.idLokasi,
                a.namaArtis,
                l.namaLokasi AS namaLokasiConcert,
                s.tanggal
            FROM 
                Setlist s
            JOIN 
                Artis a ON s.idArtis = a.idArtis
            JOIN 
                Lokasi l ON s.idLokasi = l.idLokasi
            WHERE 
                a.idArtis = ?
        """;
    
        return jdbcTemplate.query(sql, this::mapRowToArtistSetlist, idArtis);
    }

    @Override
    public long countByFilterNamaSetlist(String namaSetlist) {
        String sql = "SELECT COUNT(idSetlist) FROM Setlist WHERE namaSetlist ILIKE ?";
        return jdbcTemplate.queryForObject(sql, Long.class, "%" + namaSetlist + "%");
    }

    @Override
    public Iterable<SetlistJumlahPengguna> findSetlistByFilterNamaWithOffsetReturnWithCount(String namaSetlist, int limit, int offset) {
        String sql = "SELECT s.idSetlist, s.namaSetlist, COUNT(ps.email) as jumlahPengguna\n" + //
                        "FROM Setlist s\n" + //
                        "LEFT JOIN Pengguna_setlist ps ON s.idSetlist = ps.idSetlist\n" + //
                        "LEFT JOIN Show sh ON s.idShow = sh.idShow\n" + //
                        "WHERE s.namaSetlist ILIKE ?\n" + //
                        "GROUP BY s.idSetlist, s.namaSetlist\n" + //
                        "ORDER BY jumlahPengguna DESC \n" + //
                        "LIMIT ? OFFSET ?;\n";
        return jdbcTemplate.query(sql, this::mapRowToSetlistJumlahPengguna, "%" + namaSetlist + "%", limit, offset);
    }

    @Override
    public long maxSetlistCountForEachSetlist() {
        String sql = "SELECT MAX(jumlahPengguna) " +
                    "FROM ( " +
                        "SELECT s.idSetlist, s.namaSetlist, COUNT(ps.email) as jumlahPengguna\n" + //
                        "FROM Setlist s\n" + //
                        "LEFT JOIN Pengguna_setlist ps ON s.idSetlist = ps.idSetlist\n" + //
                        "LEFT JOIN Show sh ON s.idShow = sh.idShow\n" + //
                        "GROUP BY s.idSetlist, s.namaSetlist\n" +
                        "ORDER BY jumlahPengguna DESC \n" + //
                    ") AS artistSetlists";
        return jdbcTemplate.queryForObject(sql, Long.class);
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

    private ArtistSetlistLokasiDate mapRowToArtistSetlist(ResultSet resultSet, int rowNum) throws SQLException {
        return new ArtistSetlistLokasiDate(
            resultSet.getInt("idSetlist"),
            resultSet.getInt("idArtis"),
            resultSet.getInt("idLokasi"),
            resultSet.getString("namaArtis"),
            resultSet.getString("namaLokasiConcert"),
            resultSet.getTimestamp("tanggal").toLocalDateTime()
        );
    }

    private SetlistJumlahPengguna mapRowToSetlistJumlahPengguna(ResultSet resultSet, int rowNum) throws SQLException {
        return new SetlistJumlahPengguna(
            resultSet.getInt("idSetlist"),
            resultSet.getString("namaSetlist"),
            resultSet.getInt("jumlahPengguna")
        );
    }
}
