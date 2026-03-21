package br.com.inspectflow.domain.common.enums;

import lombok.Getter;

@Getter
public enum PartCategory {
    MECHANICAL("MECANICA"),
    ELECTRIC("ELETRICA"),
    PNEUMATIC("PNEUMATICA"),
    HYDRAULIC("HIDRAULICA"),
    OTHER("OUTRAS");

    private final String value;

    PartCategory(String value) {
        this.value = value;
    }

    public static PartCategory fromValue(String value) {
        for (PartCategory item : PartCategory.values()) {
            if (item.getValue().equals(value)) {
                return item;
            }
        }
        return null;
    }
}
