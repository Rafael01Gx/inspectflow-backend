package br.com.inspectflow.application.dashboard.dto;

public record CriticalStockDto(
        Long stockItemId,
        String name,
        String partCategory,
        int currentQuantity,
        Integer minQuantity,
        int deficit,
        String location
) {
}
