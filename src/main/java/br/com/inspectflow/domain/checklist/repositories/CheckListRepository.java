package br.com.inspectflow.domain.checklist.repositories;

import br.com.inspectflow.domain.checklist.models.Checklist;
import br.com.inspectflow.domain.common.pagination.PageRequest;
import br.com.inspectflow.domain.common.pagination.PagedResponse;
import java.util.Optional;
import java.util.List;

public interface CheckListRepository {
    Checklist save(Checklist checklist);
    Optional<Checklist> findById(String id); // Mudança para String
    List<Checklist> findAll();
    PagedResponse<Checklist> findAll(PageRequest pageRequest);
    void deleteById(String id); // Mudança para String
    Optional<Checklist> findByCode(String code);
}
