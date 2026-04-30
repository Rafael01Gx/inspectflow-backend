package br.com.inspectflow.application.order.ports.in;

import br.com.inspectflow.application.order.dto.OrderResponse;
import br.com.inspectflow.domain.common.pagination.PageRequest;
import br.com.inspectflow.domain.common.pagination.PagedResponse;
import org.springframework.security.core.Authentication;

public interface FindAllByAssigneeUseCase {
    PagedResponse<OrderResponse> execute(Authentication authUser, PageRequest pageRequest);

}
