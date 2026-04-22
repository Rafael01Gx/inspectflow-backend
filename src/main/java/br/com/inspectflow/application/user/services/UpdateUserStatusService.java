package br.com.inspectflow.application.user.services;

import br.com.inspectflow.application.http.handlers.BusinessException;
import br.com.inspectflow.application.http.handlers.UserNotFoundException;
import br.com.inspectflow.application.user.dto.UpdateUserStatusRequest;
import br.com.inspectflow.application.user.ports.in.UpdateUserStatusUseCase;
import br.com.inspectflow.domain.user.enums.Role;
import br.com.inspectflow.domain.user.models.User;
import br.com.inspectflow.domain.user.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateUserStatusService implements UpdateUserStatusUseCase {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public void execute(UUID userId, Authentication authUser , UpdateUserStatusRequest dto) {

        User userAuth = userRepository.findByEmail(authUser.getName()).orElseThrow(UserNotFoundException::new);
        if (userAuth.getId().equals(userId)) {
            throw new BusinessException("Você não tem permissões para alterar o próprio status!");
        }

        User user = userRepository.findById(userId).orElseThrow();

        validateStatusChange(userAuth, user);


        user.setActive(dto.active());
    }



    private void validateStatusChange(User userAuth, User user) {

        if (userAuth.getRole().equals(Role.GESTOR) && user.getRole().equals(Role.ADMINISTRADOR) ||  user.getRole().equals(Role.GESTOR)) {
            throw new BusinessException("Você não tem permissões suficientes para realizar a ação!");
        }
    }
}
