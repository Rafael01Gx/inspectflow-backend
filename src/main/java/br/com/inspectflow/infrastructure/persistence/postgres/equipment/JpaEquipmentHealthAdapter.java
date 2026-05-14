package br.com.inspectflow.infrastructure.persistence.postgres.equipment;

import br.com.inspectflow.domain.equipment.repositories.EquipmentHealthSheetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class JpaEquipmentHealthAdapter implements EquipmentHealthSheetRepository {
    private final PostgresEquipmentHealthRepository repository;

    @Override
    public Long countUpcomingInspections(LocalDateTime now, LocalDateTime futureDate) {
        return repository.countUpcomingInspections(now, futureDate);
    }
}
