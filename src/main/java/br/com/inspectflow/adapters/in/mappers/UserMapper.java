package br.com.inspectflow.adapters.in.mappers;

import br.com.inspectflow.domain.auth.models.SecurityUser;
import br.com.inspectflow.domain.user.dto.out.UserResponse;
import br.com.inspectflow.domain.user.enums.Role;
import br.com.inspectflow.domain.user.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public static UserResponse toUserResponse(SecurityUser user){
        return new UserResponse(user.getId(),
                user.getUsername(),
                Role.valueOf(user.getAuthorities().iterator().next().getAuthority().substring(5)),
                user.isEnabled());
    }

    public static UserResponse toUserResponse(User user) {
        return new UserResponse(user.getId(),
                user.getEmail(),
                user.getRole(),
                user.getActive());
    }
}
