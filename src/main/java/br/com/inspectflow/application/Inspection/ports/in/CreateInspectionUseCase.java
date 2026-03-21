package br.com.inspectflow.application.Inspection.ports.in;

import br.com.inspectflow.application.Inspection.dto.InspectionRequest;
import br.com.inspectflow.domain.inspection.models.Inspection;
import org.springframework.security.core.Authentication;

public interface CreateInspectionUseCase {
    Inspection execute(InspectionRequest dto, Authentication user);
}
