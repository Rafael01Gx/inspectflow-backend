package br.com.inspectflow.application.user.ports.in;

import br.com.inspectflow.application.user.dto.UpdateUserRoleRequest;
import org.springframework.security.core.Authentication;

import java.util.UUID;

public interface UpdateUserRoleUseCase {

    void execute(UUID userId, Authentication authUser, UpdateUserRoleRequest dto);
}
