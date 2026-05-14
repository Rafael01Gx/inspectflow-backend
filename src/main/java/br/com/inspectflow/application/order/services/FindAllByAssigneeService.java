package br.com.inspectflow.application.order.services;

import br.com.inspectflow.application.order.dto.OrderResponse;
import br.com.inspectflow.application.order.ports.in.FindAllByAssigneeUseCase;
import br.com.inspectflow.domain.common.pagination.PageRequest;
import br.com.inspectflow.domain.common.pagination.PagedResponse;
import br.com.inspectflow.domain.order.repositories.WorkOrderRepository;
import br.com.inspectflow.domain.user.models.User;
import br.com.inspectflow.domain.user.repositories.UserRepository;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FindAllByAssigneeService implements FindAllByAssigneeUseCase {

    private final WorkOrderRepository repository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    @Observed(name = "order.list-assignee",
            contextualName = "lista ordens por responsável")
    public PagedResponse<OrderResponse> execute(Authentication authUser, PageRequest pageRequest) {
        User user = userRepository.getReferenceByEmail(authUser.getName());
        var page = repository.findAllByAssignee(user, pageRequest);

        return new PagedResponse<>(
                page.content().stream().map(OrderResponse::from).toList(),
                page.pageNumber(),
                page.pageSize(),
                page.totalElements(),
                page.totalPages(),
                page.isLast()
        );
    }
}
