package br.com.inspectflow.infrastructure.persistence.postgres.repositories;

import br.com.inspectflow.domain.inspection.models.InspectionItem;
import br.com.inspectflow.domain.inspection.repositories.InspectionItemRepository;
import br.com.inspectflow.domain.common.pagination.PageRequest;
import br.com.inspectflow.domain.common.pagination.PagedResponse;
import br.com.inspectflow.infrastructure.persistence.common.mappers.PaginationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JpaInspectionItemRepositoryAdapter implements InspectionItemRepository {

    private final PostgresInspectionItemRepository repository;

    @Override
    public InspectionItem save(InspectionItem inspectionItem) {
        return repository.save(inspectionItem);
    }

    @Override
    public Optional<InspectionItem> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<InspectionItem> findAll() {
        return repository.findAll();
    }

    @Override
    public PagedResponse<InspectionItem> findAll(PageRequest pageRequest) {
        Pageable pageable = PaginationMapper.toPageable(pageRequest);
        Page<InspectionItem> page = repository.findAll(pageable);
        return PaginationMapper.toPagedResponse(page);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
