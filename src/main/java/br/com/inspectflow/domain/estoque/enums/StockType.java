package br.com.inspectflow.domain.estoque.enums;

import lombok.Getter;

@Getter
public enum StockType {
    EQUIPMENT("EQUIPAMENTO"),
    PIECES("PECAS"),
    OTHER("OUTROS");

    private final String value;

    StockType(String value) {
        this.value = value;
    }
}
