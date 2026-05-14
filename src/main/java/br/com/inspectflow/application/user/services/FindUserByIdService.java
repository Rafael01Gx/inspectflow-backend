package br.com.inspectflow.application.user.services;

import br.com.inspectflow.application.user.dto.UserResponse;
import br.com.inspectflow.application.http.handlers.UserNotFoundException;
import br.com.inspectflow.application.user.ports.in.FindUserByIdUseCase;
import br.com.inspectflow.domain.user.models.User;
import br.com.inspectflow.domain.user.repositories.UserRepository;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindUserByIdService implements FindUserByIdUseCase {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    @Observed(name = "user.find-id",
            contextualName = "busca usuário por id")
    public UserResponse execute(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        return new UserResponse(user.getId(),user.getName(), user.getEmail(), user.getRole(), user.isActive(),user.isMustChangePassword());
    }
}
