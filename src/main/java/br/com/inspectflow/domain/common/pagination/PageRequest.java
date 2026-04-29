package br.com.inspectflow.domain.common.pagination;

import java.util.List;

public record PageRequest(
    int page,
    int size,
    List<SortField> sort
) {
    public static PageRequest of(int page, int size, List<SortField> sort) {

        return new PageRequest(page, size, sort );
    }

    public static PageRequest of(int page, int size) {

        return new PageRequest(page, size, List.of() );
    }
}
