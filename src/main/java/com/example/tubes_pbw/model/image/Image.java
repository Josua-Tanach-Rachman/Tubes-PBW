package com.example.tubes_pbw.model.image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    private Long id;

    private String name;

    private String type;
    private String kategori;

    private byte[] imageData;
}