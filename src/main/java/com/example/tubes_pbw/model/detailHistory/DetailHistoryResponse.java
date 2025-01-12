package com.example.tubes_pbw.model.detailHistory;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DetailHistoryResponse {
    private String kategori;
    private List<PairBefAfter> lokasiShowTanggal;
    private List<TrackBeforeAfter> trackBeforeAfterList;

    public DetailHistoryResponse(){
        
    }
}
