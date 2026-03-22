package br.com.inspectflow.domain.stock.enums;

import lombok.Getter;

@Getter
public enum StockType {
    EQUIPMENT("EQUIPAMENTO"),
    PIECES("PEÇAS"),
    OTHER("OUTROS");

    private final String value;

    StockType(String value) {
        this.value = value;
    }

    public static StockType fromValue(String value) {
        for (StockType type : StockType.values()) {
            if (type.getValue().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Tipo de estoque inválido: " + value);

            }

}
