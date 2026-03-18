package br.com.inspectflow.application.user.services;

import br.com.inspectflow.application.user.dto.UserResponse;
import br.com.inspectflow.application.http.handlers.UserNotFoundException;
import br.com.inspectflow.application.user.ports.in.FindUserByEmailUseCase;
import br.com.inspectflow.domain.user.models.User;
import br.com.inspectflow.domain.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindUserByEmailService implements FindUserByEmailUseCase {

    private final UserRepository userRepository;

    @Override
    public UserResponse execute(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado!"));

        return new UserResponse(user.getId(), user.getEmail(), user.getRole(), user.isActive());
    }
}
