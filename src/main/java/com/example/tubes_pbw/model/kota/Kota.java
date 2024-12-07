package com.example.tubes_pbw.model.kota;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Kota {
    @NotBlank
    int idKota;

    @NotBlank
    String namaKota;

    @NotBlank
    int idNegara;
}
