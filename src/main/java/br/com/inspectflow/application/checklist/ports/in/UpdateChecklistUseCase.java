package br.com.inspectflow.application.checklist.ports.in;

import br.com.inspectflow.domain.checklist.models.Checklist;
import br.com.inspectflow.domain.equipment.models.Equipment;

public interface UpdateChecklistUseCase {
    Checklist execute(Equipment equipment);
}
