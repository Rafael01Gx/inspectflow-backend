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

        Sort sort;

        if (request.sort() == null || request.sort().isEmpty()) {
            sort = Sort.by(Sort.Direction.DESC, "id");
        } else {
            sort = Sort.by(
                    request.sort().stream()
                            .map(s -> {
                                Sort.Direction direction;

                                try {
                                    direction = Sort.Direction.valueOf(s.direction().toUpperCase());
                                } catch (Exception e) {
                                    direction = Sort.Direction.DESC;
                                }

                                return new Sort.Order(direction, s.field());
                            })
                            .toList()
            );
        }

        return org.springframework.data.domain.PageRequest.of(
                request.page(),
                request.size(),
                sort
        );
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
