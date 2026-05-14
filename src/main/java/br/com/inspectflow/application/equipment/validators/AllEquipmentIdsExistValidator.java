package br.com.inspectflow.application.equipment.validators;

import br.com.inspectflow.application.http.handlers.EquipmentNotFoundException;
import br.com.inspectflow.domain.equipment.models.Equipment;
import br.com.inspectflow.domain.equipment.repositories.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AllEquipmentIdsExistValidator implements EquipmentValidator<List<UUID>>{

    private final EquipmentRepository repository;

    @Override
    @Transactional(readOnly = true)
    public void execute(List<UUID> ids) {
        if (ids == null || ids.isEmpty()) return;

        List<Equipment> foundEquipments = repository.findAllById(ids);

        if (foundEquipments.size() != ids.size()) {
            List<UUID> foundIds = foundEquipments.stream().map(Equipment::getId).toList();
            List<UUID> missingIds = ids.stream()
                    .filter(id -> !foundIds.contains(id))
                    .toList();

            throw new EquipmentNotFoundException("Equipamentos não encontrados: " + missingIds);
        }
    }


}
