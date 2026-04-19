package br.com.inspectflow.application.dashboard.dto;

import br.com.inspectflow.domain.equipment.enums.EquipmentStatus;

public record EquipmentStatusCountDto(
    EquipmentStatus status,
    long count
) {}
