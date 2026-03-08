package br.com.inspectflow.domain.user.enums;

import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;

public enum Role {
    USUARIO,
    ADMINISTRADOR,
    ELETRICISTA,
    MECANICO;

    public static Role valueOf(GrantedAuthority grantedAuthority) {
        return Role.valueOf(Objects.requireNonNull(grantedAuthority.getAuthority()).substring(5));
    }
}
