package br.com.inspectflow.domain.equipment.repositories;

import br.com.inspectflow.domain.equipment.models.Equipment;
import br.com.inspectflow.domain.common.pagination.PageRequest;
import br.com.inspectflow.domain.common.pagination.PagedResponse;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface EquipmentRepository {
    Equipment save(Equipment equipment);
    Optional<Equipment> findById(UUID id);
    List<Equipment> findAll();
    PagedResponse<Equipment> findAll(PageRequest pageRequest);
    void deleteById(UUID id);
    Optional<Equipment> findByCode(String code);

    List<Equipment> findAllById(List<UUID> uuids);
}
