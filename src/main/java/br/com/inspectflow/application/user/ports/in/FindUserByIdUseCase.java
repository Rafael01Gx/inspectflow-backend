package br.com.inspectflow.application.user.ports.in;

import br.com.inspectflow.application.user.dto.UserResponse;
import java.util.UUID;

public interface FindUserByIdUseCase {
    UserResponse execute(UUID id);
}
