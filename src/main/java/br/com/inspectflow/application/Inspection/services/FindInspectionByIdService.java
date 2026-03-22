package br.com.inspectflow.application.Inspection.services;

import br.com.inspectflow.application.Inspection.ports.in.FindInspectionByIdUseCase;
import br.com.inspectflow.domain.inspection.models.Inspection;
import br.com.inspectflow.domain.inspection.repositories.InspectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindInspectionByIdService implements FindInspectionByIdUseCase {

    private final InspectionRepository repository;

    @Override
    public Inspection execute(UUID id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Inspection not found"));
    }
}
