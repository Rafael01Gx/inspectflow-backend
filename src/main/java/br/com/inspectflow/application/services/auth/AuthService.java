package br.com.inspectflow.application.services.auth;

import br.com.inspectflow.adapters.in.mappers.UserMapper;
import br.com.inspectflow.adapters.out.repositories.user.UserRepository;
import br.com.inspectflow.domain.auth.dto.in.RegisterRequest;
import br.com.inspectflow.domain.auth.dto.out.AuthResult;
import br.com.inspectflow.domain.auth.models.SecurityUser;
import br.com.inspectflow.domain.user.enums.Role;
import br.com.inspectflow.domain.user.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public AuthResult authenticate(String email, String password) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        var principal = (SecurityUser) authentication.getPrincipal();

        String token = tokenService.generateToken(authentication);

        return new AuthResult(token, UserMapper.toUserResponse(principal));
    }

    public AuthResult register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email já cadastrado");
        }

        var user = new User().builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.valueOf(request.role()))
                .active(true)
                .build();

        userRepository.save(user);

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        null,
                        Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole())));

        String token = tokenService.generateToken(authentication);

        return new AuthResult(token,  UserMapper.toUserResponse(user));
    }
}
