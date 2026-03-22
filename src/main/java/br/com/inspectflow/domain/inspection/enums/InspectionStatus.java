package br.com.inspectflow.domain.inspection.enums;

import lombok.Getter;

@Getter
public enum InspectionStatus {
    CONFORMING("Conforme"),

    CONFORMING_WITH_OBSERVATIONS("Conforme com observações"),

    NON_CONFORMING("Não conforme");

    private final String value;

    InspectionStatus(String value) {
        this.value = value;
    }

    public static InspectionStatus fromValue(String value) {
        for (InspectionStatus item : InspectionStatus.values()) {
            if (item.getValue().equals(value)) {
                return item;
            }
            return null;
        }
        return null;
    }

}
