package com.example.tubes_pbw.model.komentar;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Komentar {
    int idKomentar;
    String email;
    int idSetlist;
    String komentar;
    Timestamp tanggal;
}
