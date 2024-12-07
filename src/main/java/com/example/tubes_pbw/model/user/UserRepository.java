package com.example.tubes_pbw.model.user;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(String username);
} 
