package com.example.tubes_pbw.model.setlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class SetlistService {

    @Autowired
    private SetlistRepository setlistRepository;

    public Optional<Setlist> findByIdSetlist(int idSetlist) {
        return setlistRepository.findByIdSetlist(idSetlist);
    }

    public Iterable<Setlist> findByNamaSetlist(String namaSetlist) {
        return setlistRepository.findByNamaSetlist(namaSetlist);
    }

    public Iterable<Setlist> findAllSetlist() {
        return setlistRepository.findAllSetlist();
    }

    public Iterable<Setlist> findByShow(int idShow) {
        return setlistRepository.findByShow(idShow);
    }

    public int save(String namaSetlist, Timestamp tanggal, int idArtis, int idLokasi, String urlBukti, int idShow, String email) {
        return setlistRepository.save(namaSetlist, tanggal, idArtis, idLokasi, urlBukti, idShow, email) ;
    }

    public List<ArtistSetlistLokasiDate> findLokasiDate (int idArtis){
        return setlistRepository.findArtistSetlistLokasiDateByIdArtis(idArtis);
    }
    
    public Iterable<SetlistJumlahPengguna> findSetlistByFilterNamaWithOffsetReturnWithCount(String namaSetlist, int limit, int offset){
        return setlistRepository.findSetlistByFilterNamaWithOffsetReturnWithCount(namaSetlist, limit, offset);
    }

    public long countByFilterNamaSetlist(String namaSetlist){
        return setlistRepository.countByFilterNamaSetlist(namaSetlist);
    }

    public long maxSetlistCountForEachSetlist(){
        return setlistRepository.maxSetlistCountForEachSetlist();
    }

    public List<ArtistSetlistLokasiDate> findArtistSetlistLokasiDateByIdSetlist(int idSetlist){
        return setlistRepository.findArtistSetlistLokasiDateByIdSetlist(idSetlist);
    }

    public Iterable<SetlistDetail> findSetlistDetailByIdSetlist(int idSetlist){
        return setlistRepository.findSetlistDetailByIdSetlist(idSetlist);
    }

    public List<SetlistSong> findSetlistSongByIdSetlist(int idSetlist){
        return setlistRepository.findSetlistSongByIdSetlist(idSetlist);
    }

    public int updateSetlist(String namaSetlist,int idSetlist, Timestamp tanggal, int idLokasi, String urlBukti, int idShow, String email, Timestamp tanggalDiubah, int idLokasiBef, int idShowBef, Timestamp tanggalBef, String namaSetlistBef){
        return setlistRepository.updateSetlist(namaSetlist,idSetlist, tanggal, idLokasi, urlBukti, idShow, email, tanggalDiubah, idLokasiBef, idShowBef, tanggalBef, namaSetlistBef);
    }

    public List<SetlistSong> findSetlistSongForDetailHistory(int idSetlist, Timestamp time){
        return setlistRepository.findSetlistSongForDetailHistory(idSetlist, time);
    }

    public void setCurrentTimestamp(){
        setlistRepository.setCurrentTimestamp();
    }

    public void changeSong(int idSetlist, int idLagu, int trackNumber, String email, Timestamp tanggalDiubah, String bukti, int idLaguOld){
        setlistRepository.changeSong(idSetlist, idLagu, trackNumber, email,tanggalDiubah, bukti, idLaguOld);
    }

    public void removeSongFromSetlist(int idSetlist, int idLagu, String email, int trackNumber, Timestamp tanggalDiubah, String bukti){
        setlistRepository.removeSongFromSetlist(idSetlist, idLagu, email, trackNumber, tanggalDiubah, bukti);
    }

    public void addSongToSetlist(int idSetlist, int idLagu, String email, Timestamp tanggalDiubah, String bukti){
        setlistRepository.addSongToSetlist(idSetlist, idLagu, email, tanggalDiubah, bukti);
    }
}
