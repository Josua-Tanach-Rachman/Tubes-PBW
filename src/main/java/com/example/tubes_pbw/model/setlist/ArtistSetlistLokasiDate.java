package com.example.tubes_pbw.model.setlist;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArtistSetlistLokasiDate {
    @NotBlank
    int idSetlist;

    String namaSetlist;

    @NotBlank
    int idArtis;

    @NotBlank
    int idLokasi;

    @NotBlank
    String namaArtis;

    @NotBlank
    String namaLokasiConcert;

    @NotBlank
    LocalDateTime tanggal;
}