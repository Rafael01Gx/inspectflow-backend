package br.com.inspectflow.infrastructure.persistence.postgres.repositories;

import br.com.inspectflow.domain.Inspection_item.models.InspectionItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostgresInspectionItemRepository extends JpaRepository<InspectionItem, Long> {
}
