package br.com.inspectflow.application.order.dto;

import br.com.inspectflow.domain.order.enums.OrderPriority;
import br.com.inspectflow.domain.order.models.MaintenancePart;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UpdateOrderRequest(
        @NotNull
        UUID id,

        @Size(min = 5, max = 100, message = "O título deve ter entre 5 e 100 caracteres")
        String title,

        @Size(min = 5, message = "A descrição deve ter no mínimo 5 caracteres")
        String description,

        OrderPriority orderPriority,

        @FutureOrPresent
        LocalDate dueDate,

        List<MaintenancePart> parts,

        @FutureOrPresent
        LocalDate completionDate
) {
}
