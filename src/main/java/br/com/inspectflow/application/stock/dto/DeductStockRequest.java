package br.com.inspectflow.application.stock.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Positive;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DeductStockRequest(
        Long id,
        @Positive(message = "A quantidade deve ser maior que zero")
        Integer quantity
) {
}
