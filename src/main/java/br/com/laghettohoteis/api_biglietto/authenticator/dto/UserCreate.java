package br.com.laghettohoteis.api_biglietto.authenticator.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UserCreate(
        @NotBlank String login,
        @NotBlank String password,
        @NotBlank String name,
        @NotBlank String lastName,
        @NotNull @Size(min = 1) List<String> roles
) { }
