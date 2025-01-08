package com.example.tubes_pbw.model.lagu;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LaguJumlahSetlist {
    @NotBlank
    int idLagu;

    @NotBlank
    String namaLagu;

    @NotBlank
    int jumlahSetlist;
}
