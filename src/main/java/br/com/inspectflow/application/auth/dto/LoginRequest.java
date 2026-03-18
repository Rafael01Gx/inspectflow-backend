package br.com.inspectflow.application.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LoginRequest(
        @NotBlank(message = "O email é obrigatório!")
        @Email(message = "Formato de email inválido!")
        String email,

        @NotBlank(message = "A senha é obrigatória")
        String password
) {
}
