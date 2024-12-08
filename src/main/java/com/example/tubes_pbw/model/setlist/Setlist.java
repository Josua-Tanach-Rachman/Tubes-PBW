package com.example.tubes_pbw.model.setlist;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Setlist {
    private int idSetlist;
    
    @NotBlank
    private String namaSetlist;
    
    private java.time.LocalDateTime tanggal;
    private int idLokasi;
    private String urlBukti;
    private int idImage;
}
