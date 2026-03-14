package br.com.inspectflow.domain.inspection.repositories;

import br.com.inspectflow.domain.inspection.models.Inspection;
import br.com.inspectflow.domain.common.pagination.PageRequest;
import br.com.inspectflow.domain.common.pagination.PagedResponse;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface InspectionRepository {
    Inspection save(Inspection inspection);
    Optional<Inspection> findById(UUID id);
    List<Inspection> findAll();
    PagedResponse<Inspection> findAll(PageRequest pageRequest);
    void deleteById(UUID id);
}
