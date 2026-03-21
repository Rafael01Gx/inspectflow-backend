package br.com.inspectflow.infrastructure.persistence.postgres.repositories;

import br.com.inspectflow.domain.equipment.models.Equipment;
import br.com.inspectflow.domain.equipment.repositories.EquipmentRepository;
import br.com.inspectflow.domain.common.pagination.PageRequest;
import br.com.inspectflow.domain.common.pagination.PagedResponse;
import br.com.inspectflow.infrastructure.persistence.common.mappers.PaginationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

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
    public List<Equipment> findAll() {
        return repository.findAll();
    }

    @Override
    public PagedResponse<Equipment> findAll(PageRequest pageRequest) {
        Pageable pageable = PaginationMapper.toPageable(pageRequest);
        Page<Equipment> page = repository.findAll(pageable);
        return PaginationMapper.toPagedResponse(page);
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
}
