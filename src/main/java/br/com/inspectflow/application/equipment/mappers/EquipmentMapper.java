package br.com.inspectflow.application.equipment.mappers;

import br.com.inspectflow.application.Inspection.dto.CreateInspectionItemRequest;
import br.com.inspectflow.application.Inspection.dto.UpdateInspectionItemRequest;
import br.com.inspectflow.application.equipment.dto.CreateEquipmentComponentRequest;
import br.com.inspectflow.application.equipment.dto.CreateEquipmentRequest;
import br.com.inspectflow.application.equipment.dto.UpdateEquipmentComponentRequest;
import br.com.inspectflow.application.equipment.dto.UpdateEquipmentRequest;
import br.com.inspectflow.domain.equipment.models.Equipment;
import br.com.inspectflow.domain.equipment.models.EquipmentComponent;
import br.com.inspectflow.domain.equipment.models.EquipmentHealthSheet;
import br.com.inspectflow.domain.inspection.models.InspectionItem;

import java.util.*;
import java.util.stream.Collectors;

public class EquipmentMapper {

    public static Equipment fromCreateDto(CreateEquipmentRequest dto) {

        Equipment equipment = Equipment.builder()
                .name(dto.name())
                .code(dto.code().toUpperCase())
                .status(dto.status())
                .type(dto.type())
                .location(dto.location())
                .consignmentCodes(dto.consignmentCodes())
                .propertyCode(
                        Optional.ofNullable(dto.propertyCode())
                                .filter(s -> !s.isBlank())
                                .map(String::toUpperCase)
                                .orElse(null)
                )
                .manufacturer(dto.manufacturer())
                .model(dto.model())
                .build();

        EquipmentHealthSheet healthSheet = new EquipmentHealthSheet();

        if (dto.inspectionFrequency() != null){
            healthSheet.updateInspectionFrequency(dto.inspectionFrequency());
        }
        equipment.setHealthSheet(healthSheet);

        if (dto.components() != null) {
            dto.components().forEach(compDto -> {
                buildComponentInEquipment(equipment, compDto);
            });
        }
        return equipment;
    }

    public static Equipment fromUpdateDto(Equipment equipment, UpdateEquipmentRequest dto) {
        equipment.update(dto.name(), dto.status(), dto.type(),
                dto.location(),dto.inspectionFrequency(),dto.propertyCode(), dto.manufacturer(), dto.model());

        if (dto.components() != null) {
            Map<UUID, UpdateEquipmentComponentRequest> dtoComponentsMap = dto.components().stream()
                    .filter(c -> c.id() != null)
                    .collect(Collectors.toMap(UpdateEquipmentComponentRequest::id, c -> c));


            List<EquipmentComponent> toRemove = equipment.getComponents().stream()
                    .filter(existing -> !dtoComponentsMap.containsKey(existing.getId()))
                    .toList();

            toRemove.forEach(equipment::removeComponent);


            dto.components().forEach(compDto -> {
                if (compDto.id() == null || !dtoComponentsMap.containsKey(compDto.id())) {

                    buildComponentInEquipment(equipment, new CreateEquipmentComponentRequest(
                            compDto.name(),
                            compDto.category(),
                            mapUpdateItemsToCreateItems(compDto.items())
                    ));
                } else {
                    equipment.getComponents().stream()
                            .filter(c -> Objects.equals(c.getId(), compDto.id()))
                            .findFirst()
                            .ifPresent(existingComponent -> {
                                existingComponent.update(compDto.name(), compDto.category());
                                updateInspectionItems(existingComponent, compDto.items());
                            });
                }
            });
        }

        if (dto.consignmentCodes() != null){
            equipment.setConsignmentCodes(dto.consignmentCodes());
        }
        return equipment;
    }

    private static void updateInspectionItems(EquipmentComponent component, Set<UpdateInspectionItemRequest> itemDtos) {
        if (itemDtos == null) return;

        Map<Long, UpdateInspectionItemRequest> dtoItemsMap = itemDtos.stream()
                .filter(i -> i.id() != null)
                .collect(Collectors.toMap(UpdateInspectionItemRequest::id, i -> i));

        // REMOÇÃO
        List<InspectionItem> itemsToRemove = component.getInspectionItem().stream()
                .filter(existing -> !dtoItemsMap.containsKey(existing.getId()))
                .toList();

        itemsToRemove.forEach(component::removeInspectionItem);

        // ADIÇÃO E ATUALIZAÇÃO
        itemDtos.forEach(itemDto -> {
            if (itemDto.id() == null) {
                // ADICIONAR NOVO ITEM
                InspectionItem newItem = InspectionItem.builder()
                        .title(itemDto.title())
                        .description(itemDto.description())
                        .category(itemDto.category())
                        .impedimentItem(itemDto.impedimentItem())
                        .build();
                component.addInspectionItem(newItem);
            } else {
                // ATUALIZAR ITEM EXISTENTE
                component.getInspectionItem().stream()
                        .filter(i -> Objects.equals(i.getId(), itemDto.id()))
                        .findFirst()
                        .ifPresent(existingItem -> {
                            existingItem.update(
                                    itemDto.title(),
                                    itemDto.description(),
                                    itemDto.category(),
                                    itemDto.impedimentItem()
                            );
                        });
            }
        });
    }

    private static void buildComponentInEquipment(Equipment equipment, CreateEquipmentComponentRequest compDto) {
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
                        .build();
                component.addInspectionItem(item);
            });
        }
    }

    private static Set<CreateInspectionItemRequest> mapUpdateItemsToCreateItems(Set<UpdateInspectionItemRequest> updateItems) {
        if (updateItems == null) return Set.of();
        return updateItems.stream()
                .map(itm -> new CreateInspectionItemRequest(
                        itm.title(),
                        itm.description(),
                        itm.category(),
                        itm.impedimentItem()
                ))
                .collect(Collectors.toSet());
    }
}
