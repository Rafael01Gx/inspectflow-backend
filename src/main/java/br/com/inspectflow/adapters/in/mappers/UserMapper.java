package br.com.inspectflow.adapters.in.mappers;

import br.com.inspectflow.adapters.in.web.auth.security.SecurityUser;
import br.com.inspectflow.adapters.in.web.user.dto.UserResponse;
import br.com.inspectflow.domain.user.enums.Role;
import br.com.inspectflow.domain.user.models.User;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserMapper {
    public static UserResponse toUserResponse(SecurityUser user){

        Role role = user.getAuthorities().stream()
                .map(auth -> Objects.requireNonNull(auth.getAuthority()).replace("ROLE_", ""))
                .map(Role::valueOf)
                .findFirst()
                .orElse(Role.USUARIO);

        return new UserResponse(user.getId(),
                user.getUsername(),
                role,
                user.isEnabled());
    }

    public static UserResponse toUserResponse(User user) {
        return new UserResponse(user.getId(),
                user.getEmail(),
                user.getRole(),
                user.isActive());
    }
}
