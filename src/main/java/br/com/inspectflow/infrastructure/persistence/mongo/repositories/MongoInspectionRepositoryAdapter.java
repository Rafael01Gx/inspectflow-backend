package br.com.inspectflow.infrastructure.persistence.mongo.repositories;

import br.com.inspectflow.domain.inspection.models.Inspection;
import br.com.inspectflow.domain.inspection.repositories.InspectionRepository;
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
public class MongoInspectionRepositoryAdapter implements InspectionRepository {

    private final PostgresInspectionRepository repository;

    @Override
    public Inspection save(Inspection inspection) {
        return repository.save(inspection);
    }

    @Override
    public Optional<Inspection> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public List<Inspection> findAll() {
        return repository.findAll();
    }

    @Override
    public PagedResponse<Inspection> findAll(PageRequest pageRequest) {
        Pageable pageable = PaginationMapper.toPageable(pageRequest);
        Page<Inspection> page = repository.findAll(pageable);
        return PaginationMapper.toPagedResponse(page);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
