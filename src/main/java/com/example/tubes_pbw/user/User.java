package com.example.tubes_pbw.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class User {

    @NotNull
    @Size(min=4, max=30)
    private String username;

    @NotNull
    @Size(min=4, max=60)
    private String password;

    @NotNull
    @Size(min=4, max=60)
    private String confirmpassword;
    
    @NotNull
    @Size(min=4, max=50)
    private String name;
    
    private String role;
}