package com.example.tubes_pbw.model.lokasi;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Lokasi {
    @NotBlank
    int idLokasi;

    @NotBlank
    String namaLokasi;

    @NotBlank
    String alamatLokasi;

    @NotBlank
    int idKota;
}
