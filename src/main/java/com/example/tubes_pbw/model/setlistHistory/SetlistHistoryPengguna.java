package com.example.tubes_pbw.model.setlistHistory;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SetlistHistoryPengguna {
    private int idHistory;
    private int idSetlist;
    private String email;
    private String username;
    private Timestamp tanggalDiubah;
}
