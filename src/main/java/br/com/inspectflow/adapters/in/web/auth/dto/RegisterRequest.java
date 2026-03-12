package br.com.inspectflow.adapters.in.web.auth.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
        @NotBlank(message = "O nome é obrigatório")
        String name,
        @Email(message = "O email é obrigatório")
        String email,
        @NotBlank(message = "A role é obrigatória")
        String role,
        @NotBlank(message = "A senha é obrigatória")
        String password
) {
}
