package br.com.inspectflow.domain.equipment_component.enums;

import lombok.Getter;

@Getter
public enum EquipmentComponentCategory {
    MECHANICAL("MECANICO"),
    ELECTRIC("ELETRICO"),
    PNEUMATIC("PNEUMATICO"),
    HYDRAULIC("HIDRAULICO"),
    OTHER("OUTROS");

    private final String value;

    EquipmentComponentCategory(String value) {
        this.value = value;
    }
}
