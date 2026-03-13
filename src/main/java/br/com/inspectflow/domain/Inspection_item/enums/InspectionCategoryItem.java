package br.com.inspectflow.domain.Inspection_item.enums;

import lombok.Getter;

@Getter
public enum InspectionCategoryItem {

    MECHANICAL("MECANICO"),
    ELECTRIC("ELETRICO"),
    PNEUMATIC("PNEUMATICO"),
    HYDRAULIC("HIDRAULICO"),
    OTHER("OUTROS");

    private final String value;

    InspectionCategoryItem(String value) {
        this.value = value;
    }
}
