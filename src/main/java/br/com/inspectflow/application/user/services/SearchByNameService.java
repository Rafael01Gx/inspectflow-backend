package br.com.inspectflow.application.user.services;

import br.com.inspectflow.application.user.dto.UserResponse;
import br.com.inspectflow.application.user.ports.in.SearchByNameUseCase;
import br.com.inspectflow.domain.user.repositories.UserRepository;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchByNameService implements SearchByNameUseCase {

    private final UserRepository repository;

    @Override
    @Transactional(readOnly = true)
    @Observed(name = "user.find-name",
            contextualName = "busca usuário por nome")
    public List<UserResponse> execute(String name) {
        return repository.searchByName(name).stream().map(UserResponse::from).toList();
    }
}
