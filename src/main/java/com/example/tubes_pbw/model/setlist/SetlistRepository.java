package com.example.tubes_pbw.model.setlist;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface SetlistRepository {
    Optional<Setlist> findByIdSetlist(int idSetlist);
    Iterable<Setlist> findByNamaSetlist(String namaSetlist);
    Iterable<Setlist> findByShow(int idShow);
    int save(String namaSetlist, Timestamp tanggal, int idArtis, int idLokasi, String urlBukti, int idShow);
    List<ArtistSetlistLokasiDate> findArtistSetlistLokasiDateByIdArtis(int idArtis);
    Iterable<SetlistJumlahPengguna> findSetlistByFilterNamaWithOffsetReturnWithCount(String namaSetlist,int limit, int offset);
    long countByFilterNamaSetlist(String namaSetlist);
    long maxSetlistCountForEachSetlist();
}
