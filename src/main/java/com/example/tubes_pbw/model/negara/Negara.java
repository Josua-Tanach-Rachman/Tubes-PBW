package com.example.tubes_pbw.model.negara;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Negara {
    @NotBlank
    int idNegara;

    @NotBlank
    String namaNegara;
}
