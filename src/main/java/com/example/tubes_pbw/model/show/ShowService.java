package com.example.tubes_pbw.model.show;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShowService {

    @Autowired
    JdbcShowRepository jdbcShowRepository;


    public Optional<Show> findByIdShow(int idShow) {
        return jdbcShowRepository.findByIdShow(idShow);
    }

    public Iterable<Show> findByNamaShow(String namaShow) {
        return jdbcShowRepository.findByNamaShow(namaShow);
    }

    public int saveShow(String namaShow, int idLokasi) {
        return jdbcShowRepository.save(namaShow, idLokasi);
    }
}
