package com.example.tubes_pbw.model.setlistHistory;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SetlistHistory {
    private int idHistory;
    private int idSetlist;
    private int idArtis;
    private int idLokasi;
    private String namaSetlist;
    private Timestamp tanggal;
    private String urlBukti;
    private int idShow;
    private String email;
    private String action;
    private Timestamp tanggalDiubah;
}
