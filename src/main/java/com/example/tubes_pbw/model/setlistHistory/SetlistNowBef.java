package com.example.tubes_pbw.model.setlistHistory;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SetlistNowBef {
    Integer idShow;
    Integer idShowBef;
    Integer idLokasi;
    Integer idLokasiBef;
    Timestamp tanggal;
    Timestamp tanggalBef;
}
