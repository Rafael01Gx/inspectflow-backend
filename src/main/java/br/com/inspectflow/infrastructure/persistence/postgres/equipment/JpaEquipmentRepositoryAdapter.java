package br.com.inspectflow.infrastructure.persistence.postgres.equipment;

import br.com.inspectflow.application.equipment.dto.EquipmentListResponse;
import br.com.inspectflow.application.equipment.dto.EquipmentSummaryResponse;
import br.com.inspectflow.domain.common.pagination.PageRequest;
import br.com.inspectflow.domain.common.pagination.PagedResponse;
import br.com.inspectflow.domain.equipment.models.Equipment;
import br.com.inspectflow.domain.equipment.repositories.EquipmentRepository;
import br.com.inspectflow.infrastructure.persistence.common.mappers.PaginationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JpaEquipmentRepositoryAdapter implements EquipmentRepository {

    private final PostgresEquipmentRepository repository;

    @Override
    public Equipment save(Equipment equipment) {
        return repository.save(equipment);
    }

    @Override
    public Optional<Equipment> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public List<EquipmentListResponse> findAll() {
        return repository.findAll().stream().map(EquipmentListResponse::from).toList();
    }

    @Override
    public PagedResponse<EquipmentSummaryResponse> findAll(PageRequest pageRequest) {
        Pageable pageable = PaginationMapper.toPageable(pageRequest);
        Page<Equipment> page = repository.findAll(pageable);
        return new PagedResponse<>(
                page.getContent().stream()
                        .map(EquipmentSummaryResponse::from)
                        .toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Equipment> findByCode(String code) {
        return repository.findByCode(code);
    }

    @Override
    public List<Equipment> findAllById(List<UUID> uuids) {
        return repository.findAllById(uuids);
    }

    @Override
    public List<Equipment> findAllByCodeIn(List<String> code) {
        return repository.findAllByCodeIn(code);
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByCode(code);
    }

    @Override
    public void saveAndFlush(Equipment equipment) {
        repository.saveAndFlush(equipment);
    }

    @Override
    public List<Equipment> findTop10ByCodeContainingIgnoreCaseOrNameContainingIgnoreCase(String q, String q1) {
        return repository.findTop10ByCodeContainingIgnoreCaseOrNameContainingIgnoreCase(q,q1);
    }

    @Override
    public List<Object[]> countEquipmentsByStatus() {
        return repository.countEquipmentsByStatus();
    }
}
