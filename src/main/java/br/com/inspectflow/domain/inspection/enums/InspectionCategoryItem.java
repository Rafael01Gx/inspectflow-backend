package br.com.inspectflow.domain.inspection.enums;

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

    public static InspectionCategoryItem fromValue(String value) {
        for (InspectionCategoryItem item : InspectionCategoryItem.values()) {
            if (item.getValue().equals(value)) {
                return item;
            }
        }
        return null;
            }
}
