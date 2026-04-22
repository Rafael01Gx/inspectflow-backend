package br.com.inspectflow.application.auth.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RegisterRequest(
        @NotBlank(message = "O nome é obrigatório")
        String name,
        @NotBlank(message = "O email é obrigatório")
        @Email(message = "O email é obrigatório")
        String email,
        @NotBlank(message = "A role é obrigatória")
        String role

) {
}
