package com.example.tubes_pbw.model.setlistHistory;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SetlistNowBef {
    int idShow;
    int idShowBef;
    int idLokasi;
    int idLokasiBef;
    Timestamp tanggal;
    Timestamp tanggalBef;
}
