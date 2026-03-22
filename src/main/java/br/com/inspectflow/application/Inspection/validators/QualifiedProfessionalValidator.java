package br.com.inspectflow.application.Inspection.validators;

import br.com.inspectflow.application.Inspection.dto.InspectionComponentResultsRequest;
import br.com.inspectflow.application.Inspection.dto.InspectionRequest;
import br.com.inspectflow.application.http.handlers.UnauthorizedProfessionalException;
import br.com.inspectflow.domain.user.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QualifiedProfessionalValidator {

    public void validate(User user, InspectionRequest dto) {

        var userRole =  user.getRole();
        boolean hasForbiddenCategory = dto.componentResults().stream()
                .map(InspectionComponentResultsRequest::category)
                .anyMatch(cat -> !userRole.canHandle(cat));

        if (hasForbiddenCategory) {
            throw new UnauthorizedProfessionalException();
        }

    }
}
