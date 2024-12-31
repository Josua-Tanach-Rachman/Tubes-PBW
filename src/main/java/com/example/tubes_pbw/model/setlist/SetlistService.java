package com.example.tubes_pbw.model.setlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void save(String namaSetlist, LocalDate tanggal, int idShow, String urlBukti) {
        setlistRepository.save(namaSetlist,tanggal,idShow,urlBukti);
    }

    public List<ArtistSetlistLokasiDate> findLokasiDate (int idArtis){
        return setlistRepository.findArtistSetlistLokasiDateByIdArtis(idArtis);
    }
}
