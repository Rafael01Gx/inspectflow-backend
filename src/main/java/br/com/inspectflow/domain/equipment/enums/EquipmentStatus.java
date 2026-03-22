package br.com.inspectflow.domain.equipment.enums;

import lombok.Getter;

@Getter
public enum EquipmentStatus {
    OPERATIONAL("OPERACIONAL"),
    MAINTENANCE("MANUTENÇÃO"),
    CRITICAL("CRÍTICO"),
    DISABLED("DESABILITADO");

    private final String value;

    EquipmentStatus(String value) {
        this.value = value;
    }

    public static EquipmentStatus from(String value){
        for( EquipmentStatus item : values()){
            if(item.value.equalsIgnoreCase(value)){
                return item;
            }
            return null;
        }
        return null;
    }

}
