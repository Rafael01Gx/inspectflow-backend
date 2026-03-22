package br.com.inspectflow.application.Inspection.validators;

import br.com.inspectflow.application.Inspection.dto.InspectionComponentResultsRequest;
import br.com.inspectflow.application.Inspection.dto.InspectionRequest;
import br.com.inspectflow.application.http.handlers.UnauthorizedProfessionalException;
import br.com.inspectflow.application.http.handlers.UserNotFoundException;
import br.com.inspectflow.domain.user.models.User;
import br.com.inspectflow.domain.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QualifiedProfessionalValidator {

    private final UserRepository repository;

    public void validate(Authentication auth, InspectionRequest dto) {
        User user = repository.findByEmail(auth.getName()).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado."));
        var userRole =  user.getRole();
        boolean hasForbiddenCategory = dto.componentResults().stream()
                .map(InspectionComponentResultsRequest::category)
                .anyMatch(cat -> !userRole.canHandle(cat));

        if (hasForbiddenCategory) {
            throw new UnauthorizedProfessionalException();
        }

    }
}
