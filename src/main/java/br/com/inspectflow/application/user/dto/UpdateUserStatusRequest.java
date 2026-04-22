package br.com.inspectflow.application.user.dto;

import jakarta.validation.constraints.NotNull;

public record UpdateUserStatusRequest(

        @NotNull
        Boolean active
) {
}
