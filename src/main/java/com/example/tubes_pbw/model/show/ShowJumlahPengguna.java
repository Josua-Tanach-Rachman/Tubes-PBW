package com.example.tubes_pbw.model.show;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShowJumlahPengguna {
    @NotBlank
    int idShow;

    @NotBlank
    String namaShow;

    @NotBlank
    int jumlahPengguna;
}