package br.com.inspectflow.application.user.services;

import br.com.inspectflow.application.http.handlers.BusinessException;
import br.com.inspectflow.application.http.handlers.UserNotFoundException;
import br.com.inspectflow.application.user.dto.UpdateUserRequest;
import br.com.inspectflow.application.user.dto.UserResponse;
import br.com.inspectflow.application.user.ports.in.UpdateUserUseCase;
import br.com.inspectflow.domain.user.models.User;
import br.com.inspectflow.domain.user.repositories.UserRepository;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateUserService implements UpdateUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Override
    @Transactional
    @Observed(name = "user.update",
            contextualName = "atualiza informações de usuário")
    public UserResponse execute(UUID id, Authentication auth, UpdateUserRequest dto) {

        User userDetails = userRepository.findByEmail(auth.getName()).orElseThrow(UserNotFoundException::new);

        if (!id.equals(userDetails.getId())) {
            log.atInfo().log("Usuário {} tentou alterar dados de segurança de outro usuário: {}", userDetails.getId(), id);
            throw new BusinessException("Você não tem permissão para realizar esta operação.");
        }

        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        if (dto.password() != null) {
            user.setPassword(encoder.encode(dto.password()));
        }

        if (dto.name() != null) {
            user.setName(dto.name());
        }

        return new UserResponse(user.getId(),user.getName() ,user.getEmail(), user.getRole(), user.isActive(),user.isMustChangePassword());
    }
}