package br.com.inspectflow.application.user.services;

import br.com.inspectflow.adapters.in.web.user.dto.UpdateUserRequest;
import br.com.inspectflow.adapters.in.web.user.dto.UserResponse;
import br.com.inspectflow.application.http.handlers.UserNotFoundException;
import br.com.inspectflow.application.user.ports.in.UpdateUserUseCase;
import br.com.inspectflow.domain.user.models.User;
import br.com.inspectflow.domain.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateUserService implements UpdateUserUseCase {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserResponse execute(UUID id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado!"));

        User updatedUser = User.builder()
                .id(user.getId())
                .name(request.name())
                .email(request.email())
                .password(user.getPassword()) // Mantém a senha original
                .role(request.role())
                .active(request.active())
                .createdAt(user.getCreatedAt())
                .build();

        User savedUser = userRepository.save(updatedUser);
        return new UserResponse(savedUser.getId(), savedUser.getEmail(), savedUser.getRole(), savedUser.isActive());
    }
}
