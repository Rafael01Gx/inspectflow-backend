package br.com.inspectflow.domain.order.enums;

import lombok.Getter;

@Getter
public enum OrderPriority {
    LOW("BAIXA"),
    MEDIUM("MEDIA"),
    HIGH("ALTA");

    private final String value;

    OrderPriority(String value) {
        this.value = value;
    }


}
