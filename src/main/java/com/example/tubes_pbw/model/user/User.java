package com.example.tubes_pbw.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @NotBlank
    int idPengguna;

    @NotNull
    @Size(min=4, max=30)
    private String username;

    @NotNull
    @Size(min=4, max=60)
    private String password;
    
    @NotNull
    @Size(min=4, max=50)
    private String nama;
    
    @NotBlank
    private String role;

    @NotNull
    @Size(min=4, max=60)
    private String confirmpassword;
    
    public User(String username,String password, String nama, String role) {
        this.username = username;
        this.password = password;
        this.nama = nama;
        this.role = role;
    }
}