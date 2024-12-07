package com.example.tubes_pbw.user;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(String username);
} 
