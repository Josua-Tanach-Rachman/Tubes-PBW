package com.example.tubes_pbw.model.komentar;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KomentarPengguna {
    int idKomentar;
    String email;
    String username;
    String komentar;
    Timestamp tanggal;
}
