package com.example.tubes_pbw.model.detailHistory;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PairBefAfter {
    String kategori;
    String before;
    String after;

    public PairBefAfter(){
        
    }
}
