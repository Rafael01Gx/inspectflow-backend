package br.com.inspectflow.application.dashboard.dto;

public record TopPartUsedDto(
        Long stockItemId,
        String partName,
        String partCategory,
        long totalUsed,
        long usedInOrders,
        int currentStock,
        Integer minQuantity
) {
}
