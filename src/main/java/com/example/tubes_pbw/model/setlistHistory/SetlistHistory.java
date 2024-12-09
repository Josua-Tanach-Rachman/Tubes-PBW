package com.example.tubes_pbw.model.setlistHistory;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SetlistHistory {
    private int idHistory;

    private int idSetlist;
    private String namaSetlist;
    private LocalDateTime tanggal;
    private String urlBukti;
    private int idShow;
    private String action;

    private LocalDateTime tanggalDiubah;
}
