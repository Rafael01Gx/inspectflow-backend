package br.com.inspectflow.application.user.dto;

import br.com.inspectflow.domain.user.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UpdateUserRequest(
        String name,

        @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
        String password
) {
}
