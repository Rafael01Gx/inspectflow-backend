package br.com.inspectflow.domain.equipamento.enums;

import lombok.Getter;

@Getter
public enum EquipmentStatus {
    OPERATIONAL("OPERACIONAL"),
    MAINTENANCE("MANUTENÇÃO"),
    CRITICAL("CRITICO"),
    DISABLED("DESABILITADO");

    private final String value;

    EquipmentStatus(String value) {
        this.value = value;
    }

}
