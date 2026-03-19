package br.com.inspectflow.domain.inspection.repositories;

import br.com.inspectflow.domain.inspection.models.InspectionItem;
import br.com.inspectflow.domain.common.pagination.PageRequest;
import br.com.inspectflow.domain.common.pagination.PagedResponse;
import java.util.Optional;
import java.util.List;

public interface InspectionItemRepository {
    InspectionItem save(InspectionItem inspectionItem);
    Optional<InspectionItem> findById(Long id);
    List<InspectionItem> findAll();
    PagedResponse<InspectionItem> findAll(PageRequest pageRequest);
    void deleteById(Long id);
}
