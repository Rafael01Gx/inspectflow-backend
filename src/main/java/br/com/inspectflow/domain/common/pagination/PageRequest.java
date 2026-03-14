package br.com.inspectflow.domain.common.pagination;

public record PageRequest(
    int page,
    int size,
    String sortField,
    String sortDirection
) {
    public static PageRequest of(int page, int size) {
        return new PageRequest(page, size, null, null);
    }
}
