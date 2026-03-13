package br.com.inspectflow.domain.equipment.enums;

import lombok.Getter;

@Getter
public enum EquipmentType {
    ELECTRICAL_PANEL("PAINEL_ELETRICO"),
    MACHINE("MAQUINA"),
    PUMP("BOMBA"),
    COMPRESSOR("COMPRESSOR"),
    SENSOR("SENSOR"),
    VALVE("VALVULA"),
    OTHER("OUTROS");

    private final String value;

    EquipmentType(String value) {
        this.value = value;
    }
}
