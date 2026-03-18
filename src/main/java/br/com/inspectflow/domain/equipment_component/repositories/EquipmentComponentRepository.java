package br.com.inspectflow.domain.equipment_component.repositories;

import br.com.inspectflow.domain.equipment_component.models.EquipmentComponent;
import br.com.inspectflow.domain.common.pagination.PageRequest;
import br.com.inspectflow.domain.common.pagination.PagedResponse;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface EquipmentComponentRepository {
    EquipmentComponent save(EquipmentComponent equipmentComponent);
    Optional<EquipmentComponent> findById(UUID id);
    List<EquipmentComponent> findAll();
    PagedResponse<EquipmentComponent> findAll(PageRequest pageRequest);
    void deleteById(UUID id);

    List<EquipmentComponent> findAllByEquipmentIdIn(List<UUID> equipmentsIds);
}
