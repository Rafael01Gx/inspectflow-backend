package br.com.inspectflow.domain.equipment.repositories;

import java.time.LocalDateTime;

public interface EquipmentHealthSheetRepository {

    Long countUpcomingInspections(LocalDateTime now, LocalDateTime futureDate);
}
