package br.com.inspectflow.infrastructure.persistence.common.mappers;

import br.com.inspectflow.domain.common.pagination.PageRequest;
import br.com.inspectflow.domain.common.pagination.PagedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PaginationMapper {

    public static Pageable toPageable(PageRequest request) {
        if (request == null) {
            return Pageable.unpaged();
        }
        
        Sort sort = Sort.unsorted();
        if (request.sortField() != null && !request.sortField().isEmpty()) {
            Sort.Direction direction = Sort.Direction.ASC;
            if ("DESC".equalsIgnoreCase(request.sortDirection())) {
                direction = Sort.Direction.DESC;
            }
            sort = Sort.by(direction, request.sortField());
        }
        
        return org.springframework.data.domain.PageRequest.of(request.page(), request.size(), sort);
    }

    public static <T> PagedResponse<T> toPagedResponse(Page<T> page) {
        return new PagedResponse<>(
            page.getContent(),
            page.getNumber(),
            page.getSize(),
            page.getTotalElements(),
            page.getTotalPages(),
            page.isLast()
        );
    }
}
