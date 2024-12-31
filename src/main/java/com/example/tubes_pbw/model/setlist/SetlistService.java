package com.example.tubes_pbw.model.setlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
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

    public int save(String namaSetlist, Timestamp tanggal, int idArtis, int idLokasi, String urlBukti) {
        return setlistRepository.save(namaSetlist, tanggal, idArtis, idLokasi, urlBukti) ;
    }

    public List<ArtistSetlistLokasiDate> findLokasiDate (int idArtis){
        return setlistRepository.findArtistSetlistLokasiDateByIdArtis(idArtis);
    }
}
