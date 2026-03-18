package br.com.inspectflow.application.checklist.services;

import br.com.inspectflow.application.checklist.ports.in.FindAllCheckListUseCase;
import br.com.inspectflow.domain.checklist.models.Checklist;
import br.com.inspectflow.domain.checklist.repositories.CheckListRepository;
import br.com.inspectflow.domain.common.pagination.PageRequest;
import br.com.inspectflow.domain.common.pagination.PagedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindAllCheckListService implements FindAllCheckListUseCase {

    private final CheckListRepository repository;

    @Override
    public PagedResponse<Checklist> execute(PageRequest pageRequest) {
        var page = repository.findAll(pageRequest);
        return new PagedResponse<>(
                page.content(),
                page.pageNumber(),
                page.pageSize(),
                page.totalElements(),
                page.totalPages(),
                page.isLast()
        );
    }
}
