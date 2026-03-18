package br.com.inspectflow.application.equipment.ports.in;

import java.util.UUID;

public interface DisableEquipmentUseCase {
    void execute(UUID id);
}
