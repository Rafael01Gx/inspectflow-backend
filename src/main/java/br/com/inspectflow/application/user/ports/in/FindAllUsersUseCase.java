package br.com.inspectflow.application.user.ports.in;

import br.com.inspectflow.adapters.in.web.user.dto.UserResponse;
import br.com.inspectflow.domain.common.pagination.PageRequest;
import br.com.inspectflow.domain.common.pagination.PagedResponse;

public interface FindAllUsersUseCase {
    PagedResponse<UserResponse> execute(PageRequest pageRequest);
}
