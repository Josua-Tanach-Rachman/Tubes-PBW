package com.example.tubes_pbw.model.setlist;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SetlistJumlahPengguna {
    @NotBlank
    int idSetlist;

    @NotBlank
    String namaSetlist;

    @NotBlank
    int jumlahPengguna;
}