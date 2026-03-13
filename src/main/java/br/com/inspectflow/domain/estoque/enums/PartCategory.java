package br.com.inspectflow.domain.estoque.enums;

import lombok.Getter;

@Getter
public enum PartCategory {
    MECHANICAL("MECANICO"),
    ELECTRIC("ELETRICO"),
    PNEUMATIC("PNEUMATICO"),
    HYDRAULIC("HIDRAULICO"),
    OTHER("OUTROS");

    private final String value;

    PartCategory(String value) {
        this.value = value;
    }
}
