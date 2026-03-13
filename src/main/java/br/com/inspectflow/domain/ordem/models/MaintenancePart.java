package br.com.inspectflow.domain.ordem.models;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
@Embeddable
public record MaintenancePart(
        Long stockId,
        @NotBlank
        String name,
        @NotBlank
        Integer quantity,
        boolean isFromStock
) {
}
