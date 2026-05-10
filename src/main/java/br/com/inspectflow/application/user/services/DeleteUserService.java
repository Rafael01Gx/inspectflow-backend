package br.com.inspectflow.application.user.services;

import br.com.inspectflow.application.http.handlers.UserNotFoundException;
import br.com.inspectflow.application.user.ports.in.DeleteUserUseCase;
import br.com.inspectflow.domain.user.repositories.UserRepository;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteUserService implements DeleteUserUseCase {

    private final UserRepository userRepository;

    @Override
    @Transactional
    @Observed(name = "user.delete",
            contextualName = "deletar um usuário")
    public void execute(UUID id) {
        if (userRepository.findById(id).isEmpty()) {
            throw new UserNotFoundException();
        }
        userRepository.deleteById(id);
    }
}
