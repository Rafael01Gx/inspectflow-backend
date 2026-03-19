package br.com.inspectflow.application.checklist.services;

import br.com.inspectflow.application.checklist.mappers.ChecklistMapper;
import br.com.inspectflow.domain.checklist.models.Checklist;
import br.com.inspectflow.domain.checklist.repositories.CheckListRepository;
import br.com.inspectflow.domain.equipment.models.Equipment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChecklistSyncService {

    private final CheckListRepository checkListRepository;

    public String syncFromEquipment(Equipment equipment) {
        String existingId = equipment.getChecklistId();

        Checklist checklist = ChecklistMapper.fromEquipment(equipment,existingId);

        Checklist savedChecklist = checkListRepository.save(checklist);
        return savedChecklist.getId();
    }
}
