package br.com.inspectflow.domain.equipment.enums;

import lombok.Getter;

@Getter
public enum InspectionFrequency {
    SEMANAL(7),
    QUINZENAL(15),
    MENSAL(30),
    BIMESTRAL(60),
    TRIMESTRAL(90);

    private final int dias;

    InspectionFrequency(int dias) {
        this.dias = dias;
    }

    public static InspectionFrequency fromDias(int dias) {
        for (InspectionFrequency i : InspectionFrequency.values()) {
            if (i.getDias() == dias) return i;
        }
        throw new IllegalArgumentException("Intervalo inválido: " + dias);
    }
}
