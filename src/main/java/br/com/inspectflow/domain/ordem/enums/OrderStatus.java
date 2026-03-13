package br.com.inspectflow.domain.ordem.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PENDING("PENDENTE"),
    IN_PROGRESS("EM ANDAMENTO"),
    COMPLETED("CONCLUIDO"),
    CANCELLED("CANCELADO");

    private final String value;

    OrderStatus(String description) {
        this.value = description;
    }
}
