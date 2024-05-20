package edu.comillas.icai.gitt.pat.spring.p5.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.Map;

public record OrderRequest(
        @NotBlank
        String restaurant,
        @NotBlank
        @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$", message = "La fecha debe tener el formato DD/MM/YYYY")
        String date,
        @NotNull
        Map<String, Integer> orders) {
}
