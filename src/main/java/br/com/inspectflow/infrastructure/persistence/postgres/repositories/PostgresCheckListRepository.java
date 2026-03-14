package br.com.inspectflow.infrastructure.persistence.postgres.repositories;

import br.com.inspectflow.domain.checklist.models.Checklist;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PostgresCheckListRepository extends JpaRepository<Checklist, Long> {
    Optional<Checklist> findByCode(String code);
}
