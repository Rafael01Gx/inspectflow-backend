package br.com.inspectflow.domain.user.enums;

import br.com.inspectflow.domain.common.enums.PartCategory;
import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;

public enum Role {

    ADMINISTRADOR {
        @Override
        public boolean canHandle(PartCategory category) {
            return true;
        }
    },
    LIDER,
    SUPERVISOR,
    GESTOR,

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

    public static Role fromAuthority(GrantedAuthority grantedAuthority) {
        String authority = grantedAuthority.getAuthority();
        if (authority == null) return null;

        String roleName = authority.startsWith("ROLE_") ? authority.substring(5) : authority;
        return Role.valueOf(roleName);
    }
}
