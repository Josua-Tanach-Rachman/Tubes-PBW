package com.example.tubes_pbw.model.user;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(String username);
    int save(User user) throws Exception;
    int updateUser(int idPengguna, String nama, String username, String password, String email);
    List<User> findAll ();
} 
