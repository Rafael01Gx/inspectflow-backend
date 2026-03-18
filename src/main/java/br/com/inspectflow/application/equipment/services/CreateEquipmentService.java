package br.com.inspectflow.application.equipment.services;

import br.com.inspectflow.application.checklist.services.ChecklistSyncService;
import br.com.inspectflow.application.equipment.dto.CreateEquipmentRequest;
import br.com.inspectflow.application.equipment.dto.EquipmentResponse;
import br.com.inspectflow.application.equipment.mappers.EquipmentMapper;
import br.com.inspectflow.application.equipment.ports.in.CreateEquipmentUseCase;
import br.com.inspectflow.application.equipment.validators.UniqueEquipmentCodeValidation;
import br.com.inspectflow.domain.equipment.models.Equipment;
import br.com.inspectflow.domain.equipment.repositories.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateEquipmentService implements CreateEquipmentUseCase {

    private final EquipmentRepository repository;
    private final UniqueEquipmentCodeValidation validation;
    private final ChecklistSyncService checklistSyncService;

    @Override
    @Transactional
    public EquipmentResponse execute(CreateEquipmentRequest dto) {

        validation.execute(dto.code());

        Equipment equipment = EquipmentMapper.fromCreateDto(dto);

        Equipment savedEquipment = repository.save(equipment);

        String checklistId = checklistSyncService.syncFromEquipment(savedEquipment);

        savedEquipment.setChecklistId(checklistId);
        savedEquipment = repository.save(savedEquipment);

        return EquipmentResponse.from(savedEquipment);
    }
}
