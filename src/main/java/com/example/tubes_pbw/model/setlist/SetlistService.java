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

    public Iterable<Setlist> findByShow(int idShow) {
        return setlistRepository.findByShow(idShow);
    }

    public int save(String namaSetlist, Timestamp tanggal, int idArtis, int idLokasi, String urlBukti, int idShow) {
        return setlistRepository.save(namaSetlist, tanggal, idArtis, idLokasi, urlBukti, idShow) ;
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

    public int updateSetlist(int idSetlist, Timestamp tanggal, int idArtis, int idLokasi, String urlBukti, int idShow){
        setlistRepository.setCurrentTimestamp();
        return setlistRepository.updateSetlist(idSetlist, tanggal, idArtis, idLokasi, urlBukti, idShow);
    }
}
