package br.com.inspectflow.infrastructure.persistence.postgres.repositories;

import br.com.inspectflow.domain.checklist.models.Checklist;
import br.com.inspectflow.domain.checklist.repositories.CheckListRepository;
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
public class JpaCheckListRepositoryAdapter implements CheckListRepository {

    private final PostgresCheckListRepository repository;

    @Override
    public Checklist save(Checklist checklist) {
        return repository.save(checklist);
    }

    @Override
    public Optional<Checklist> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Checklist> findAll() {
        return repository.findAll();
    }

    @Override
    public PagedResponse<Checklist> findAll(PageRequest pageRequest) {
        Pageable pageable = PaginationMapper.toPageable(pageRequest);
        Page<Checklist> page = repository.findAll(pageable);
        return PaginationMapper.toPagedResponse(page);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Checklist> findByCode(String code) {
        return repository.findByCode(code);
    }
}
