package com.example.tubes_pbw.model.image;

import java.util.Optional;

public interface ImageRepository {
    public void save(Image image);
    public Optional<Image> findByName(String name);
}