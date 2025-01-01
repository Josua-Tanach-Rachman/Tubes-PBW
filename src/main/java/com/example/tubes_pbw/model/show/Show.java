package com.example.tubes_pbw.model.show;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.sql.Date;

@Data
@AllArgsConstructor
public class Show {
    private int idShow;
    private String namaShow;
    private int idLokasi;
    private Date beginDate;
    private Date endDate;
}
