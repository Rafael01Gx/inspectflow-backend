package br.com.inspectflow.application.dashboard.dto;

public record TopEquipmentByOrdersDto(
        String equipmentId,
        String equipmentName,
        long totalOrders,
        long completedOrders,
        long inProgressOrders,
        Double avgResolutionHours
) {
}
