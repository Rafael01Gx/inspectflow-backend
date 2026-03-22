package br.com.inspectflow.domain.user.enums;

import br.com.inspectflow.domain.common.enums.PartCategory;
import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;

public enum Role {
    USUARIO,
    ADMINISTRADOR {
        @Override
        public boolean canHandle(PartCategory category) {
            return true;
        }
    },
    INSPETOR,

    ELETRICISTA {
        @Override
        public boolean canHandle(PartCategory category) {

            return category == PartCategory.ELECTRIC;
        }
    },
    MECANICO {
        @Override
        public boolean canHandle(PartCategory category) {
            return category != PartCategory.ELECTRIC;
        }
    };

    public boolean canHandle(PartCategory category) {
        return false;
    }

    public static Role valueOf(GrantedAuthority grantedAuthority) {
        return Role.valueOf(Objects.requireNonNull(grantedAuthority.getAuthority()).substring(5));
    }
}
