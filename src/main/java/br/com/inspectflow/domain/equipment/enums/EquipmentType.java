package br.com.inspectflow.domain.equipment.enums;

import lombok.Getter;

@Getter
public enum EquipmentType {
    ELECTRICAL_PANEL("PAINEL_ELÉTRICO"),
    MACHINE("MÁQUINA"),
    PUMP("BOMBA"),
    COMPRESSOR("COMPRESSOR"),
    SENSOR("SENSOR"),
    VALVE("VÁLVULA"),
    OTHER("OUTROS");

    private final String value;

    EquipmentType(String value) {
        this.value = value;
    }
}
