package com.example.tubes_pbw.model.setlistHistory;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LaguNowBef {
    int idLagu;
    int idLaguBef;
    int trackNumber;
    String action;
}
