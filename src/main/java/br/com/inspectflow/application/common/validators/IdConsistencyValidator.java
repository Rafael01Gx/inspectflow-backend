package br.com.inspectflow.application.common.validators;

import br.com.inspectflow.application.http.handlers.IdMismatchException;
import org.springframework.stereotype.Component;

@Component
public class IdConsistencyValidator<T>{

    public void execute(T pathId, T bodyId) {
        if (pathId == null || !pathId.equals(bodyId)) {
            throw new IdMismatchException();
        }
    }

}
