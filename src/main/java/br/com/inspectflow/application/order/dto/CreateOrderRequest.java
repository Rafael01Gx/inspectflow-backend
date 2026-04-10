package br.com.inspectflow.application.order.dto;

import br.com.inspectflow.domain.order.enums.OrderPriority;
import br.com.inspectflow.domain.order.models.MaintenancePart;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CreateOrderRequest(

        @NotBlank
        @Size(min = 5, max = 100)
        String title,

        @NotBlank
        @Size(min = 5, max = 100)
        String description,

        @NotBlank
        @Size(min = 3, max = 100)
        String equipmentName,

        @NotNull
        UUID equipmentId,

        @NotNull
        OrderPriority orderPriority,

        @NotNull
        @Future
        LocalDate dueDate,

        List<MaintenancePart> parts


) {
}
