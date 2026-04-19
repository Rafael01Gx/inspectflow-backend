package br.com.inspectflow.application.stock.dto;

import br.com.inspectflow.domain.stock.models.StockItemUsage;

import java.time.LocalDateTime;
import java.util.UUID;

public record StockItemUsageResponse(
        Long id,
        Long stockItemId,
        String stockItemName,
        UUID workOrderId,
        Integer quantityUsed,
        LocalDateTime usedAt
) {
    public static StockItemUsageResponse from(StockItemUsage stockItemUsage) {
        return new StockItemUsageResponse(
                stockItemUsage.getId(),
                stockItemUsage.getStockItem().getId(),
                stockItemUsage.getStockItem().getName(),
                stockItemUsage.getWorkOrder().getId(),
                stockItemUsage.getQuantityUsed(),
                stockItemUsage.getUsedAt());

    }
}
