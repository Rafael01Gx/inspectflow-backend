package br.com.inspectflow.application.equipment.dto;

import br.com.inspectflow.domain.equipment.enums.InspectionFrequency;
import br.com.inspectflow.domain.equipment.models.EquipmentHealthSheet;

import java.time.LocalDateTime;
import java.util.UUID;

public record EquipmentHealthSheetResponse(
        UUID id,
        InspectionFrequency mechanicalInspectionFrequency ,
        LocalDateTime lastMechanicalInspection,
        LocalDateTime nextMechanicalInspection,

        InspectionFrequency electricalInspectionFrequency ,
        LocalDateTime lastElectricalInspection,
        LocalDateTime nextElectricalInspection,
        InspectionFrequency calibrationInspectionFrequency ,
        LocalDateTime lastCalibration,
        LocalDateTime nextCalibration
) {
    public static EquipmentHealthSheetResponse from(EquipmentHealthSheet healthSheet) {
        return new EquipmentHealthSheetResponse(
                healthSheet.getId(),
                healthSheet.getMechanicalInspectionFrequency(),
                healthSheet.getLastMechanicalInspection(),
                healthSheet.getNextMechanicalInspection(),
                healthSheet.getElectricalInspectionFrequency(),
                healthSheet.getLastElectricalInspection(),
                healthSheet.getNextElectricalInspection(),
                healthSheet.getCalibrationInspectionFrequency(),
                healthSheet.getLastCalibration(),
                healthSheet.getNextCalibration()
        );
    }

}
