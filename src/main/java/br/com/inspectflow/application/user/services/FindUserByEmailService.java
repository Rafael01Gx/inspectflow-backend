package br.com.inspectflow.application.user.services;

import br.com.inspectflow.application.user.dto.UserResponse;
import br.com.inspectflow.application.http.handlers.UserNotFoundException;
import br.com.inspectflow.application.user.ports.in.FindUserByEmailUseCase;
import br.com.inspectflow.domain.user.models.User;
import br.com.inspectflow.domain.user.repositories.UserRepository;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FindUserByEmailService implements FindUserByEmailUseCase {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    @Observed(name = "user.find-email",
            contextualName = "busca usuário por email")
    public UserResponse execute(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        return new UserResponse(user.getId(), user.getName(),user.getEmail(), user.getRole(), user.isActive(),user.isMustChangePassword());
    }
}
