package br.com.inspectflow.application.order.dto;

import br.com.inspectflow.domain.order.models.MaintenancePart;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UpdateOrderRequest(
        @NotBlank
        String id,

        @Size(min = 5, max = 100)
        String title,

        @Size(min = 5, max = 100)
        String description,

        String orderStatus,

        String orderPriority,

        @Future
        LocalDate dueDate,

        Set<MaintenancePart> parts,

        @Size(min = 10)
        String performedWork,

        @FutureOrPresent
        LocalDate completionDate
) {
}
