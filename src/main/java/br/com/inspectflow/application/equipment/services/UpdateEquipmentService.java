package br.com.inspectflow.application.equipment.services;

import br.com.inspectflow.application.checklist.services.ChecklistSyncService;
import br.com.inspectflow.application.common.validators.IdConsistencyValidator;
import br.com.inspectflow.application.equipment.dto.EquipmentResponse;
import br.com.inspectflow.application.equipment.dto.UpdateEquipmentRequest;
import br.com.inspectflow.application.equipment.mappers.EquipmentMapper;
import br.com.inspectflow.application.equipment.ports.in.UpdateEquipmentUseCase;
import br.com.inspectflow.application.http.handlers.EquipmentNotFoundException;
import br.com.inspectflow.domain.equipment.models.Equipment;
import br.com.inspectflow.domain.equipment.repositories.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateEquipmentService implements UpdateEquipmentUseCase {
    private final EquipmentRepository repository;
    private final IdConsistencyValidator<UUID> idValidator;
    private final ChecklistSyncService checklistSyncService;

    @Override
    @Transactional
    public EquipmentResponse execute(UUID id, UpdateEquipmentRequest dto) {
       idValidator.execute(id,dto.id());

       var equipment = repository.findById(id).orElseThrow(EquipmentNotFoundException::new);

        EquipmentMapper.fromUpdateDto(equipment,dto);

        Equipment savedEquipment = repository.save(equipment);

        String checklistId = checklistSyncService.syncFromEquipment(savedEquipment);

        if (savedEquipment.getChecklistId() == null) {
             savedEquipment.setChecklistId(checklistId);
             savedEquipment = repository.save(savedEquipment);
        }

        return EquipmentResponse.from(savedEquipment);
    }
}
