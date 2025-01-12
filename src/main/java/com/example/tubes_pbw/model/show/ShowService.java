package com.example.tubes_pbw.model.show;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ShowService {

    @Autowired
    ShowRepository showRepository;


    public Optional<Show> findByIdShow(int idShow) {
        return showRepository.findByIdShow(idShow);
    }

    public Iterable<Show> findByNamaShow(String namaShow) {
        return showRepository.findByNamaShow(namaShow);
    }

    public Iterable<Show> findByIdLokasi(int  idLokasi) {
        return showRepository.findByIdLokasi(idLokasi);
    }

    public int saveShow(String namaShow, int idLokasi, Date beginDate, Date endDate) {
        return showRepository.save(namaShow, idLokasi, beginDate, endDate);
    }

    public Iterable<ShowJumlahPengguna> findShowByFilterNamaWithOffsetReturnWithCount(String namaShow, int limit, int offset){
        return showRepository.findShowByFilterNamaWithOffsetReturnWithCount(namaShow, limit, offset);
    }

    public long countByFilterNamaShow(String namaShow){
        return showRepository.countByFilterNamaShow(namaShow);
    }

    public long maxSetlistCountForEachShow(){
        return showRepository.maxSetlistCountForEachShow();
    }

    public long countByIwasThere(int idShow){
        return showRepository.countByIwasThere(idShow);
    }

    public String findConcertCity(int idShow) {
        return showRepository.findConcertCity(idShow);
    }

    public String findConcertAddress(int idShow){
        return showRepository.findConcertAddress(idShow);
    }

    public List<ArtisNamaOnly> findAristOnConcert (int idShow){
        return showRepository.findAristOnConcert(idShow);
    }
}
