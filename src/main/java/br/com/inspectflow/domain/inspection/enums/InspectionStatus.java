package br.com.inspectflow.domain.inspection.enums;

public enum InspectionStatus {
    CONFORMING("Conforme"),

    CONFORMING_WITH_OBSERVATIONS("Conforme com observações"),

    NON_CONFORMING("Não conforme");

    private final String value;

    InspectionStatus(String value) {
        this.value = value;
    }

    public static String fromValue(String value) {
        for (InspectionCategoryItem item : InspectionCategoryItem.values()) {
            if (item.getValue().equals(value)) {
                return item.getValue();
            }
            return null;
        }
        return null;
    }

}
