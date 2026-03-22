package br.com.inspectflow.application.Inspection.ports.in;

import br.com.inspectflow.domain.inspection.models.Inspection;

import java.util.UUID;

public interface FindInspectionByIdUseCase {
    Inspection execute(UUID id);
}
