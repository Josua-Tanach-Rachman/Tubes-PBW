package com.example.tubes_pbw.model.negara;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NegaraService {
    @Autowired
    NegaraRepository repo;

    public Optional<Negara> findByNamaNegara(String namaNegara) {
        return repo.findByNamaNegara(namaNegara);
    }

    public Iterable<Negara> findByFilterNamaNegara(String namaNegara){
        return repo.findByFilterNamaNegara(namaNegara);
    }
}
