package com.example.tubes_pbw.model.negara;

import java.util.List;
import java.util.Optional;

public interface NegaraRepository {
    Optional<Negara> findByNamaNegara(String namaNegara);
    Iterable<Negara> findByFilterNamaNegara(String namaNegara);
    List<Negara> findByIdNegara(int idNegara);
}
