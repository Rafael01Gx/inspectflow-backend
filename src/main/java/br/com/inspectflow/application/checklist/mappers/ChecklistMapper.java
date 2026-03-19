package br.com.inspectflow.application.checklist.mappers;

import br.com.inspectflow.domain.checklist.models.Checklist;
import br.com.inspectflow.domain.checklist.models.ChecklistItem;
import br.com.inspectflow.domain.equipment.models.Equipment;
import br.com.inspectflow.domain.equipment.models.EquipmentComponent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ChecklistMapper {

    public static Checklist fromEquipment(Equipment equipment, String existingId) {
        return Checklist.builder()
                .id(existingId) // Mantém o ID se existir (Update) ou null (Create)
                .code("CHK-" + equipment.getCode())
                .name("Checklist - " + equipment.getName())
                .equipmentId(equipment.getId())
                .equipmentName(equipment.getName())
                .items(toChecklistItem(equipment.getComponents()))
                .build();
    }

    private static List<ChecklistItem> toChecklistItem(Set<EquipmentComponent> components) {
        if (components == null) return new ArrayList<>();
        
        List<ChecklistItem> items = new ArrayList<>();
        components.forEach(c -> {
            if (c.getInspectionItem() != null) {
                c.getInspectionItem().forEach(item -> {
                    items.add(ChecklistItem.builder()
                            .componentName(c.getName())
                            .title(item.getTitle())
                            .description(item.getDescription())
                            .category(item.getCategory()) // Mantém como Enum
                            .impedimentItem(item.isImpedimentItem())
                            .build());
                });
            }
        });

        return items.stream()
                .sorted(Comparator.comparing(ChecklistItem::getComponentName))
                .collect(Collectors.toList());
    }
}
