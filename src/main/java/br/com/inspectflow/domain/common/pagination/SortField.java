package br.com.inspectflow.domain.common.pagination;

public record SortField(
        String field,
        String direction
) {
}
