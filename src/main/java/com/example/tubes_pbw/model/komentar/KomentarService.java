package com.example.tubes_pbw.model.komentar;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KomentarService {

    @Autowired
    private KomentarRepository komentarRepository;

    public Optional<Komentar> findByIdKomentar(int idKomentar) {
        return komentarRepository.findByIdKomentar(idKomentar);
    }

    public Iterable<Komentar> findBySetlistId(int idSetlist) {
        return komentarRepository.findBySetlistId(idSetlist);
    }

    public Iterable<Komentar> findByPenggunaId(int idPengguna) {
        return komentarRepository.findByPenggunaId(idPengguna);
    }

    public int save(int idPengguna, int idSetlist, String komentar) {
        return komentarRepository.save(idPengguna, idSetlist, komentar);
    }

    public Iterable<KomentarPengguna> findKomentarPenggunaBySetlistId(int idSetlist){
        return komentarRepository.findKomentarPenggunaBySetlistId(idSetlist);
    }
}
