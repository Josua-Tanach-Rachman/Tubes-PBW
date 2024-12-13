package com.example.tubes_pbw.model.show;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Show {
    int idShow;
    String namaShow;
    int idLokasi;
    String urlGambarShow;
}
