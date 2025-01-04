package com.example.tubes_pbw.model.lagu;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LaguTanggalShow {
    private Timestamp tanggal;
    private int idShow;
    private String namaShow;
}
