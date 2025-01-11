package com.example.tubes_pbw.model.setlist;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface SetlistRepository {
    Optional<Setlist> findByIdSetlist(int idSetlist);
    Iterable<Setlist> findByNamaSetlist(String namaSetlist);
    Iterable<Setlist> findByShow(int idShow);
    int save(String namaSetlist, Timestamp tanggal, int idArtis, int idLokasi, String urlBukti, int idShow, String email);
    List<ArtistSetlistLokasiDate> findArtistSetlistLokasiDateByIdArtis(int idArtis);
    Iterable<SetlistJumlahPengguna> findSetlistByFilterNamaWithOffsetReturnWithCount(String namaSetlist,int limit, int offset);
    long countByFilterNamaSetlist(String namaSetlist);
    long maxSetlistCountForEachSetlist();

    List<ArtistSetlistLokasiDate> findArtistSetlistLokasiDateByIdSetlist(int idSetlist);
    Iterable<SetlistDetail> findSetlistDetailByIdSetlist(int idSetlist);

    List<SetlistSong> findSetlistSongByIdSetlist(int idSetlist);

    int updateSetlist(String namaSetlist, int idSetlist, Timestamp tanggal, int idLokasi, String urlBukti, int idShow, String email, Timestamp tanggalDiubah, int idLokasiBef, int idShowBef, Timestamp tanggalBef, String namaSetlistBef);

    void setCurrentTimestamp();

    List<SetlistSong> findSetlistSongForDetailHistory(int idSetlist, Timestamp time);

    void changeSong(int idSetlist, int idLagu, int trackNumber, String email, Timestamp tanggalDiubah, String bukti, int idLaguOld);
    void removeSongFromSetlist(int idSetlist, int idLagu, String email, int trackNumber, Timestamp tanggalDiubah, String bukti);
    void addSongToSetlist(int idSetlist, int idLagu, String email, Timestamp tanggalDiubah, String bukti);
}
