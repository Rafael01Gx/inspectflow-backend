package br.com.inspectflow.application.checklist.ports.in;

import br.com.inspectflow.domain.checklist.models.Checklist;
import br.com.inspectflow.domain.common.pagination.PageRequest;
import br.com.inspectflow.domain.common.pagination.PagedResponse;

public interface FindAllCheckListUseCase {
    PagedResponse<Checklist> execute(PageRequest pageRequest);
}
