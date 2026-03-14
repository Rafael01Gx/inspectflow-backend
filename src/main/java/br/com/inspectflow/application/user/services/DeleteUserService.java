package br.com.inspectflow.application.user.services;

import br.com.inspectflow.application.http.handlers.UserNotFoundException;
import br.com.inspectflow.application.user.ports.in.DeleteUserUseCase;
import br.com.inspectflow.domain.user.repositories.UserRepository;
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
    public void execute(UUID id) {
        if (!userRepository.findById(id).isPresent()) {
            throw new UserNotFoundException("Usuário não encontrado!");
        }
        userRepository.deleteById(id);
    }
}
