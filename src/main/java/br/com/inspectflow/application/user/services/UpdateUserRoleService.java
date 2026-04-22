package br.com.inspectflow.application.user.services;

import br.com.inspectflow.application.http.handlers.BusinessException;
import br.com.inspectflow.application.http.handlers.UserNotFoundException;
import br.com.inspectflow.application.user.dto.UpdateUserRoleRequest;
import br.com.inspectflow.application.user.ports.in.UpdateUserRoleUseCase;
import br.com.inspectflow.domain.user.models.User;
import br.com.inspectflow.domain.user.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateUserRoleService implements UpdateUserRoleUseCase {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public void execute(UUID userId, Authentication authUser , UpdateUserRoleRequest dto) {
        User userDetails = userRepository.findByEmail(authUser.getName()).orElseThrow(UserNotFoundException::new);

        if (userDetails.getId().equals(userId)) {
            throw new BusinessException("Você não tem permissões para alterar a própria função!");
        }

        User user = userRepository.findById(userId).orElseThrow();

        if (!user.getRole().equals(dto.role())) {
            user.setRole(dto.role());
        }

    }
}
