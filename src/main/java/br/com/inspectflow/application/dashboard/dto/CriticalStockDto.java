package br.com.inspectflow.application.dashboard.dto;

public record CriticalStockDto(
        Long stockItemId,
        String name,
        String partCategory,
        Integer currentQuantity,
        Integer minQuantity,
        Integer deficit,
        String location
) {
}
