package com.example.tubes_pbw.model.setlist;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Setlist {
    private int idSetlist;
    
    @NotBlank
    private String namaSetlist;
    
    private int idArtis; 
    
    private int idLokasi;
    
    private LocalDateTime tanggal;
    
    private String urlBukti; 
    
    private int idShow;
}
