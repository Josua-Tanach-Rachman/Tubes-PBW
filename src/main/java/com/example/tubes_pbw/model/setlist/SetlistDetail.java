package com.example.tubes_pbw.model.setlist;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SetlistDetail {
    private int idSetlist;
    private int idArtis;
    private String namaArtis;
    private int idLokasi;
    private String namaLokasi;
    private Timestamp tanggal;
    private String email;
    private String namaPembuat;
    private int idLagu;
    private String namaLagu;
    private int trackNumber;
}
