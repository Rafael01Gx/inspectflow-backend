package br.com.inspectflow.infrastructure.persistence.postgres.repositories;

import br.com.inspectflow.domain.user.enums.Role;
import br.com.inspectflow.domain.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface PostgresUserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    User getReferenceByEmail(String email);


    List<User> findTop5ByNameStartingWithIgnoreCaseOrderByNameAsc(String name);

    @Query("SELECT u.email FROM User u WHERE u.role IN :roles")
    List<String> findEmailByRoleIn(@Param("roles") Set<Role> roles);
}
