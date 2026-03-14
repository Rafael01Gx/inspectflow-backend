package br.com.inspectflow.application.user.services;

import br.com.inspectflow.adapters.in.web.user.dto.CreateUserRequest;
import br.com.inspectflow.adapters.in.web.user.dto.UserResponse;
import br.com.inspectflow.application.user.ports.in.CreateUserUseCase;
import br.com.inspectflow.domain.user.models.User;
import br.com.inspectflow.domain.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateUserService implements CreateUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserResponse execute(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email já cadastrado!");
        }

        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(request.role())
                .active(true)
                .build();

        User savedUser = userRepository.save(user);
        return new UserResponse(savedUser.getId(), savedUser.getEmail(), savedUser.getRole(), savedUser.isActive());
    }
}
