package br.com.inspectflow.domain.equipment.enums;

import lombok.Getter;

@Getter
public enum AttachmentType {
    TECHNICAL("TECNICO"),
    OPERATION("OPERACAO"),
    MAINTENANCE("MANUTENCAO"),
    SAFETY("SEGURANCA"),
    ;

    private final String value;

    AttachmentType(String value) {
        this.value = value;
    }

    public static AttachmentType fromValue(String value) {
        for (AttachmentType type : values()) {
            if (type.getValue().equalsIgnoreCase(value)) {
                return type;
            }
            return null;
        }
        return null;
    };
}
