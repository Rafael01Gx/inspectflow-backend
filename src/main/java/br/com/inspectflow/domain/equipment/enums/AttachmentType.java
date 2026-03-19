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
}
