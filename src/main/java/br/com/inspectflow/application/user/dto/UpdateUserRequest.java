package br.com.inspectflow.application.user.dto;

import br.com.inspectflow.domain.user.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UpdateUserRequest(
        @NotBlank String name,
        @NotBlank @Email String email,
        @NotNull Role role,
        @NotNull Boolean active
) {
}
