package br.com.inspectflow.application.Inspection.services;

import br.com.inspectflow.application.Inspection.ports.in.FindInspectionByIdUseCase;
import br.com.inspectflow.domain.inspection.models.Inspection;
import br.com.inspectflow.domain.inspection.repositories.InspectionRepository;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindInspectionByIdService implements FindInspectionByIdUseCase {

    private final InspectionRepository repository;

    @Override
    @Transactional(readOnly = true)
    @Observed(name = "inspection.find-id",
            contextualName = "Busca inspeção por id")
    @Cacheable(value = "inspectionById", key = "#id.toString()")
    public Inspection execute(UUID id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Inspection not found"));
    }
}
