package br.com.inspectflow.application.user.dto;

import br.com.inspectflow.domain.user.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CreateUserRequest(
        @NotBlank(message = "O nome é obrigatório")
        String name,
        @NotBlank(message = "O email é obrigatório")
        @Email(message = "O email é obrigatório")
        String email,
        @NotNull(message = "A role é obrigatória")
        Role role
) {
}
