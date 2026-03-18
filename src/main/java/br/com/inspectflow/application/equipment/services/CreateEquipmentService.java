package br.com.inspectflow.application.equipment.services;

import br.com.inspectflow.application.equipment.dto.CreateEquipmentRequest;
import br.com.inspectflow.application.equipment.ports.in.CreateEquipmentUseCase;
import br.com.inspectflow.application.equipment_component.dto.EquipmentResponse;
import br.com.inspectflow.domain.Inspection_item.enums.InspectionStatus;
import br.com.inspectflow.domain.Inspection_item.models.InspectionItem;
import br.com.inspectflow.domain.equipment.models.Equipment;
import br.com.inspectflow.domain.equipment.repositories.EquipmentRepository;
import br.com.inspectflow.domain.equipment_component.models.EquipmentComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateEquipmentService implements CreateEquipmentUseCase {

    private final EquipmentRepository repository;

    @Override
    @Transactional
    public EquipmentResponse execute(CreateEquipmentRequest dto) {
        
        Equipment equipment = Equipment.builder()
                .name(dto.name())
                .code(dto.code().toUpperCase())
                .status(dto.status())
                .type(dto.type())
                .location(dto.location())
                .build();

        if (dto.components() != null) {
            dto.components().forEach(compDto -> {
                EquipmentComponent component = EquipmentComponent.builder()
                        .name(compDto.name())
                        .category(compDto.category())
                        .build();

                equipment.addComponent(component);

                if (compDto.items() != null) {
                    compDto.items().forEach(itemDto -> {
                        InspectionItem item = InspectionItem.builder()
                                .title(itemDto.title())
                                .description(itemDto.description())
                                .category(itemDto.category())
                                .impedimentItem(itemDto.impedimentItem())
                                .observation(itemDto.observation())
                                .status(InspectionStatus.OK) // IREI REMOVER AQUI
                                .build();
                        component.addInspectionItem(item);
                    });
                }
            });
        }
        Equipment savedEquipment = repository.save(equipment);

        return EquipmentResponse.from(savedEquipment);
    }
}
