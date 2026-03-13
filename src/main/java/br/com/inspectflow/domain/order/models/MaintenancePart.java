package br.com.inspectflow.domain.order.models;

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
