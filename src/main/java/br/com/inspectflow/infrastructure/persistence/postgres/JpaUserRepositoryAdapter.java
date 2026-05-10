package br.com.inspectflow.infrastructure.persistence.postgres;

import br.com.inspectflow.domain.user.enums.Role;
import br.com.inspectflow.domain.user.models.User;
import br.com.inspectflow.domain.user.repositories.UserRepository;
import br.com.inspectflow.domain.common.pagination.PageRequest;
import br.com.inspectflow.domain.common.pagination.PagedResponse;
import br.com.inspectflow.infrastructure.persistence.common.mappers.PaginationMapper;
import br.com.inspectflow.infrastructure.persistence.postgres.repositories.PostgresUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JpaUserRepositoryAdapter implements UserRepository {

    private final PostgresUserRepository repository;

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public List<String> findEmailByRoleIn(Set<Role> roles) {
        return repository.findEmailByRoleIn(roles);
    }

    @Override
    public PagedResponse<User> findAll(PageRequest pageRequest) {
        Pageable pageable = PaginationMapper.toPageable(pageRequest);
        Page<User> page = repository.findAll(pageable);
        return PaginationMapper.toPagedResponse(page);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public User getReferenceById(UUID id) {
        return repository.getReferenceById(id);
    }

    @Override
    public User getReferenceByEmail(String email) {
        return repository.getReferenceByEmail(email);
    }

    @Override
    public List<User> searchByName(String name) {
        return repository.findTop5ByNameStartingWithIgnoreCaseOrderByNameAsc(name);
    }
}
