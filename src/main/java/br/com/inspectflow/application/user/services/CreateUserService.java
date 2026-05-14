package br.com.inspectflow.application.user.services;

import br.com.inspectflow.application.http.handlers.DuplicationFoundException;
import br.com.inspectflow.application.notification.services.NotificationGroupMemberService;
import br.com.inspectflow.application.user.dto.CreateUserRequest;
import br.com.inspectflow.application.user.dto.UserResponse;
import br.com.inspectflow.application.user.events.CreateUserEvent;
import br.com.inspectflow.application.user.events.publisher.CreateUserEventPublisher;
import br.com.inspectflow.application.user.ports.in.CreateUserUseCase;
import br.com.inspectflow.domain.user.models.User;
import br.com.inspectflow.domain.user.repositories.UserRepository;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateUserService implements CreateUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final NotificationGroupMemberService memberService;
    private final CreateUserEventPublisher eventPublisher;

    @Override
    @Transactional
    @Observed(name = "user.create",
    contextualName = "criar novo usuário")
    public UserResponse execute(CreateUserRequest dto) {

        if (userRepository.existsByEmail(dto.email())) {
            throw new DuplicationFoundException("Email já cadastrado!");
        }

        String tempPassword = UUID.randomUUID().toString().substring(0, 8);

        User user = User.builder()
                .name(dto.name())
                .email(dto.email())
                .password(passwordEncoder.encode(tempPassword))
                .role(dto.role())
                .active(true)
                .build();

        User savedUser = userRepository.save(user);

        memberService.addMemberByRole(savedUser.getId(),savedUser.getRole());

        eventPublisher.publishCreated(new CreateUserEvent(user.getEmail(), user.getName(), tempPassword));

        return new UserResponse(savedUser.getId(), savedUser.getName(),savedUser.getEmail(), savedUser.getRole(), savedUser.isActive(),user.isMustChangePassword());
    }

}
