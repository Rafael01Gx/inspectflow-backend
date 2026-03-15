package br.com.inspectflow.application.common.validators;

import br.com.inspectflow.application.http.handlers.IdMismatchException;

public class IdConsistencyValidator {

    public static void validate(Long pathId, Long bodyId) {
        if (pathId == null || !pathId.equals(bodyId)) {
            throw new IdMismatchException();
        }
    }

}
