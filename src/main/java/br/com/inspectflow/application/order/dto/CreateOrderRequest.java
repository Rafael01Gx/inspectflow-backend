package br.com.inspectflow.application.order.dto;

import br.com.inspectflow.domain.order.enums.OrderPriority;
import br.com.inspectflow.domain.order.models.MaintenancePart;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CreateOrderRequest(

        @NotBlank(message = "O título é obrigatório")
        @Size(min = 5, max = 100, message = "O título deve ter entre 5 e 100 caracteres")
        String title,

        @NotBlank(message = "A descrição é obrigatória")
        @Size(min = 5, max = 100, message = "A descrição deve ter entre 5 e 100 caracteres")
        String description,

        @NotBlank(message = "O nome do equipamento é obrigatório")
        @Size(min = 3, max = 100, message = "O nome do equipamento deve ter entre 3 e 100 caracteres")
        String equipmentName,

        @NotNull(message = "O equipamento é obrigatório")
        UUID equipmentId,

        @NotNull(message = "A prioridade é obrigatória")
        OrderPriority orderPriority,

        @NotNull
        @FutureOrPresent(message = "A data de planejamento deve ser futura ou atual")
        LocalDate dueDate,

        List<MaintenancePart> parts


) {
}
