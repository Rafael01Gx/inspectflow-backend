package br.com.inspectflow.domain.checklist.repository;

import br.com.inspectflow.domain.checklist.models.Checklist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckListRepository extends JpaRepository<Checklist, Long> {
}
