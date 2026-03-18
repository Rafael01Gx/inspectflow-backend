package br.com.inspectflow.infrastructure.persistence.postgres.repositories;

import br.com.inspectflow.domain.equipment_component.models.EquipmentComponent;
import br.com.inspectflow.domain.equipment_component.repositories.EquipmentComponentRepository;
import br.com.inspectflow.domain.common.pagination.PageRequest;
import br.com.inspectflow.domain.common.pagination.PagedResponse;
import br.com.inspectflow.infrastructure.persistence.common.mappers.PaginationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JpaEquipmentComponentRepositoryAdapter implements EquipmentComponentRepository {

    private final PostgresEquipmentComponentRepository repository;

    @Override
    public EquipmentComponent save(EquipmentComponent equipmentComponent) {
        return repository.save(equipmentComponent);
    }

    @Override
    public Optional<EquipmentComponent> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public List<EquipmentComponent> findAll() {
        return repository.findAll();
    }

    @Override
    public PagedResponse<EquipmentComponent> findAll(PageRequest pageRequest) {
        Pageable pageable = PaginationMapper.toPageable(pageRequest);
        Page<EquipmentComponent> page = repository.findAll(pageable);
        return PaginationMapper.toPagedResponse(page);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public List<EquipmentComponent> findAllByEquipmentIdIn(List<UUID> equipmentsIds) {
        return repository.findAllByEquipmentIdIn(equipmentsIds);
    }
}
