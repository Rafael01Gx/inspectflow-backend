package br.com.inspectflow.application.stock.dto;

import jakarta.validation.constraints.Positive;

public record DeductStockRequest(
        Long id,
        @Positive(message = "A quantidade deve ser maior que zero")
        Integer quantity
) {
}
