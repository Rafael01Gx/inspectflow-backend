package br.com.inspectflow.application.Inspection.ports.in;

import br.com.inspectflow.domain.inspection.models.InspectionHistory;

import java.util.List;
import java.util.UUID;

public interface FindByEquipmentIdUseCase {

    List<InspectionHistory> execute(UUID equipmentId);
}
