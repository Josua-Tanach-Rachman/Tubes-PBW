package com.example.tubes_pbw.model.detailHistory;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TrackBeforeAfter {
    int tracknumber;
    String namaLaguSebelumnya;
    String namaLaguSetelahnya;

    public TrackBeforeAfter(){
        
    }
}
