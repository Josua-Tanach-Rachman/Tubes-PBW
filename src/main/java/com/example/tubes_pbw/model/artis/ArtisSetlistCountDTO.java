package com.example.tubes_pbw.model.artis;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArtisSetlistCountDTO {
    @NotBlank
    int idArtis;

    @NotBlank
    String namaArtis;

    long jumlahSetlist;
}
