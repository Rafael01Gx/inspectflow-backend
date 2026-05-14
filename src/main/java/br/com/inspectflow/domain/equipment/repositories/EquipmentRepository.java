package br.com.inspectflow.domain.equipment.repositories;

import br.com.inspectflow.application.equipment.dto.EquipmentSummaryResponse;
import br.com.inspectflow.domain.common.pagination.PageRequest;
import br.com.inspectflow.domain.common.pagination.PagedResponse;
import br.com.inspectflow.domain.equipment.models.Equipment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EquipmentRepository {
    Equipment save(Equipment equipment);
    Optional<Equipment> findById(UUID id);
    List<EquipmentSummaryResponse> findAll();
    PagedResponse<EquipmentSummaryResponse> findAll(PageRequest pageRequest);
    void deleteById(UUID id);
    Optional<Equipment> findByCode(String code);

    List<Equipment> findAllById(List<UUID> uuids);

    List<Equipment> findAllByCodeIn(List<String> code);

    boolean existsByCode(String code);

    void saveAndFlush(Equipment equipment);

    List<Equipment> findTop10ByCodeContainingIgnoreCaseOrNameContainingIgnoreCase(String q, String q1);

    List<Object[]> countEquipmentsByStatus();
}
