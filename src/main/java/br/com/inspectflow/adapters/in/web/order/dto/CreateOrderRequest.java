package br.com.inspectflow.adapters.in.web.order.dto;

import br.com.inspectflow.domain.order.models.MaintenancePart;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

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

        @NotBlank
        String equipmentId,

        @NotBlank
        String orderPriority,

        @NotBlank
        @Future
        LocalDate dueDate,

        @NotBlank
        @Size(min = 3, max = 100)
        String assignee,

        List<MaintenancePart> parts


) {
}
