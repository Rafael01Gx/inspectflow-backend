package br.com.inspectflow.application.checklist.ports.in;

import br.com.inspectflow.domain.checklist.models.Checklist;

public interface FindByIdUseCase {

    Checklist execute(String id);
}
