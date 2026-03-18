package br.com.inspectflow.application.equipment.validators;

import br.com.inspectflow.application.http.handlers.EquipmentNotFoundException;
import br.com.inspectflow.domain.equipment.models.Equipment;
import br.com.inspectflow.domain.equipment.repositories.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AllEquipmentCodeExistValidator {

    private final EquipmentRepository repository;


    public void execute(List<String> code) {
        if (code == null || code.isEmpty()) return;

        List<Equipment> foundEquipments = repository.findAllByCodeIn(code);

        if (foundEquipments.size() != code.size()) {
            List<String> foundCodes = foundEquipments.stream().map(Equipment::getCode).toList();
            List<String> missingCodes = code.stream()
                    .filter(cd -> !foundCodes.contains(cd))
                    .toList();

            throw new EquipmentNotFoundException("Equipamentos não encontrados: " + missingCodes);
        }
    }
}
