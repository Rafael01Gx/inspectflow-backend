package br.com.inspectflow.adapters.in.web.user.controller;

import br.com.inspectflow.adapters.in.mappers.UserMapper;
import br.com.inspectflow.adapters.in.web.auth.security.SecurityUser;
import br.com.inspectflow.application.user.dto.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(@AuthenticationPrincipal SecurityUser user) {
        return ResponseEntity.ok(UserMapper.toUserResponse(user));
    }
}
