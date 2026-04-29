package br.com.inspectflow.adapters.in.mappers;

import br.com.inspectflow.domain.common.pagination.PageRequest;
import br.com.inspectflow.domain.common.pagination.SortField;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class PageableRequestMapper {

    public static PageRequest fromRequest(Pageable page){
        List<SortField> sortFields = page.getSort().isSorted()
                ? page.getSort().stream()
                  .map(order -> new SortField(
                          order.getProperty(),
                          order.getDirection().name()
                  ))
                  .toList()
                : List.of();

        return PageRequest.of(
                page.getPageNumber(),
                page.getPageSize(),
                sortFields
        );
    }
}
