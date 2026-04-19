package br.com.inspectflow.domain.inspection.repositories;

import br.com.inspectflow.domain.common.pagination.PageRequest;
import br.com.inspectflow.domain.common.pagination.PagedResponse;
import br.com.inspectflow.domain.inspection.models.Inspection;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InspectionRepository {
    Inspection save(Inspection inspection);
    Optional<Inspection> findById(UUID id);
    List<Inspection> findAll();
    PagedResponse<Inspection> findAll(PageRequest pageRequest);
    void deleteById(UUID id);

    long count();
    long countByDateBetweenAndStatusNotIn(LocalDateTime startDate, LocalDateTime endDate);


    long countCompletedAndOnTimeInspections(LocalDateTime now);

    long countAllInspectionsUpTo(LocalDateTime now);
}
