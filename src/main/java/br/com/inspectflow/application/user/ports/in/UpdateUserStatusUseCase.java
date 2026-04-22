package br.com.inspectflow.application.user.ports.in;

import br.com.inspectflow.application.user.dto.UpdateUserStatusRequest;
import org.springframework.security.core.Authentication;

import java.util.UUID;

public interface UpdateUserStatusUseCase {
    void execute(UUID userId, Authentication authUser , UpdateUserStatusRequest dto);
}
