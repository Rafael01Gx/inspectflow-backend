package br.com.inspectflow.domain.stock.enums;

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
}
