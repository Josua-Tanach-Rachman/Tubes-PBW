package com.example.tubes_pbw.model.setlist;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SetlistSong {
    private int idSetlist;
    private int idLagu;
    private int trackNumber;
    private String namaLagu;
}
