package br.com.inspectflow.domain.user.repositories;

import br.com.inspectflow.domain.user.enums.Role;
import br.com.inspectflow.domain.user.models.User;
import br.com.inspectflow.domain.common.pagination.PageRequest;
import br.com.inspectflow.domain.common.pagination.PagedResponse;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.List;

public interface UserRepository {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    User save(User user);
    Optional<User> findById(UUID id);
    List<User> findAll();
    List<String> findEmailByRoleIn(Set<Role> roles);
    PagedResponse<User> findAll(PageRequest pageRequest);
    void deleteById(UUID id);

    User getReferenceById(UUID id);

    User getReferenceByEmail(String email);

    List<User> searchByName(String name);
}
