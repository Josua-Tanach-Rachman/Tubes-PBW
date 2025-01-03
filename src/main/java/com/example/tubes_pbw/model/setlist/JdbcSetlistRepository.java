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

    @Override
    public List<ArtistSetlistLokasiDate> findArtistSetlistLokasiDateByIdSetlist(int idSetlist){
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
                s.idSetlist = ?
        """;
    
        return jdbcTemplate.query(sql, this::mapRowToArtistSetlist, idSetlist);
    }

    @Override
    public Iterable<SetlistDetail> findSetlistDetailByIdSetlist(int idSetlist){
        String sql = "SELECT \n" + //
                        "\ts.idSetlist,\n" + //
                        "    a.idArtis, \n" + //
                        "    a.namaArtis, \n" + //
                        "    l.idLokasi, \n" + //
                        "    l.namaLokasi, \n" + //
                        "    s.tanggal, \n" + //
                        "    p.email, \n" + //
                        "    p.nama AS namaPembuat, \n" + //
                        "    sl.idLagu, \n" + //
                        "    lg.namaLagu, \n" + //
                        "    sl.trackNumber\n" + //
                        "FROM Setlist s\n" + //
                        "JOIN Artis a ON s.idArtis = a.idArtis\n" + //
                        "JOIN Lokasi l ON s.idLokasi = l.idLokasi\n" + //
                        "JOIN Setlist_lagu sl ON s.idSetlist = sl.idSetlist\n" + //
                        "JOIN Lagu lg ON sl.idLagu = lg.idLagu\n" + //
                        "JOIN Pengguna p ON s.email = p.email\n" + //
                        "WHERE s.idSetlist = ?\n" + //
                        "ORDER BY tracknumber;";
        return jdbcTemplate.query(sql, this::mapRowToSetlistDetail,idSetlist);
    }

    @Override
    public List<SetlistSong> findSetlistSongByIdSetlist(int idSetlist) {
        String sql = "SELECT sll.idsetlist,sll.idlagu, sll.tracknumber, l.namalagu \n" + //
                        "FROM setlistlagu sll\n" + //
                        "JOIN lagu l ON sll.idlagu = l.idlagu\n" + //
                        "where sll.idsetlist = ?\n" + //
                        "order by sll.tracknumber";
        return jdbcTemplate.query(sql, this::mapRowToSetlistSong,idSetlist);
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

    private SetlistDetail mapRowToSetlistDetail(ResultSet resultSet, int rowNum) throws SQLException {
        return new SetlistDetail(
            resultSet.getInt("idSetlist"),
            resultSet.getInt("idArtis"),
            resultSet.getString("namaArtis"),
            resultSet.getInt("idLokasi"),
            resultSet.getString("namaLokasi"),
            resultSet.getTimestamp("tanggal"),
            resultSet.getString("email"),
            resultSet.getString("namaPembuat"),
            resultSet.getInt("idLagu"),
            resultSet.getString("namaLagu"),
            resultSet.getInt("trackNumber")
        );
    }

    private SetlistSong mapRowToSetlistSong(ResultSet resultSet, int rowNum) throws SQLException {
        return new SetlistSong(
            resultSet.getInt("idSetlist"),
            resultSet.getInt("idLagu"),
            resultSet.getInt("trackNumber"),
            resultSet.getString("namaLagu")
        );
    }
    
       
}
