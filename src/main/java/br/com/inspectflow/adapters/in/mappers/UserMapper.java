package br.com.inspectflow.adapters.in.mappers;

import br.com.inspectflow.application.http.handlers.UserNotFoundException;
import br.com.inspectflow.application.user.services.SecurityUser;
import br.com.inspectflow.application.user.dto.UserResponse;
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
                .orElseThrow(()-> new UserNotFoundException("Erro ao buscar permissão do usuário"));

        return new UserResponse(user.getId(),
                user.getName(),
                user.getUsername(),
                role,
                user.isEnabled());
    }

    public static UserResponse toUserResponse(User user) {
        return new UserResponse(user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.isActive());
    }
}
