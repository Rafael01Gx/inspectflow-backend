package br.com.inspectflow.application.dashboard.dto;

public record EquipmentResolutionDto(
        String equipmentId,
        String equipmentName,
        Double avgResolutionHours,
        long totalCompleted,
        Double minResolutionHours,
        Double maxResolutionHours
) {
}
