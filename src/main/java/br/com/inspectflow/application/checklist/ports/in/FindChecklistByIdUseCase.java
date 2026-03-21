package br.com.inspectflow.application.checklist.ports.in;

import br.com.inspectflow.domain.checklist.models.Checklist;

public interface FindChecklistByIdUseCase {

    Checklist execute(String id);
}
