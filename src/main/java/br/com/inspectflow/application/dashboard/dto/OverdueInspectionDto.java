package br.com.inspectflow.application.dashboard.dto;

import java.time.LocalDateTime;

public record OverdueInspectionDto(
        String equipmentId,
        String equipmentName,
        String overdueType,
        long overdueDays,
        LocalDateTime lastMechanicalInspection,
        LocalDateTime lastElectricalInspection,
        LocalDateTime lastCalibration,
        LocalDateTime nextMechanicalInspection,
        LocalDateTime nextElectricalInspection,
        LocalDateTime nextCalibration
) {
}
