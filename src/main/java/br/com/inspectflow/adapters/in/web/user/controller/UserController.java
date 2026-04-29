package br.com.inspectflow.adapters.in.web.user.controller;

import br.com.inspectflow.adapters.in.mappers.PageableRequestMapper;
import br.com.inspectflow.adapters.in.mappers.UserMapper;
import br.com.inspectflow.application.auth.dto.RegisterRequest;
import br.com.inspectflow.application.auth.ports.in.RegisterUseCase;
import br.com.inspectflow.application.user.dto.UpdateUserRequest;
import br.com.inspectflow.application.user.dto.UpdateUserRoleRequest;
import br.com.inspectflow.application.user.dto.UpdateUserStatusRequest;
import br.com.inspectflow.application.user.dto.UserResponse;
import br.com.inspectflow.application.user.ports.in.FindAllUsersUseCase;
import br.com.inspectflow.application.user.ports.in.UpdateUserRoleUseCase;
import br.com.inspectflow.application.user.ports.in.UpdateUserStatusUseCase;
import br.com.inspectflow.application.user.ports.in.UpdateUserUseCase;
import br.com.inspectflow.application.user.services.SecurityUser;
import br.com.inspectflow.domain.common.pagination.PagedResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final FindAllUsersUseCase findAllUsers;
    private final UpdateUserUseCase updateUser;
    private final UpdateUserRoleUseCase updateUserRole;
    private final UpdateUserStatusUseCase updateUserStatus;
    private final RegisterUseCase register;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(@AuthenticationPrincipal SecurityUser user) {
        return ResponseEntity.ok(UserMapper.toUserResponse(user));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<PagedResponse<UserResponse>> all(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(findAllUsers.execute(PageableRequestMapper.fromRequest(pageable)));
    }

    @PatchMapping("/{id}/edit")
    public ResponseEntity<UserResponse> edit(@RequestBody @Valid UpdateUserRequest dto, @PathVariable UUID id, Authentication user) {
        return ResponseEntity.ok(updateUser.execute(id,user,dto));
    }

    @PreAuthorize("hasRole('GESTOR')")
    @PatchMapping("/{id}/role")
    public ResponseEntity<Void> editRole(@RequestBody @Valid UpdateUserRoleRequest dto, @PathVariable UUID id,Authentication authUser) {
        updateUserRole.execute(id,authUser,dto);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('GESTOR')")
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> editStatus(@RequestBody @Valid UpdateUserStatusRequest dto, @PathVariable UUID id, Authentication authUser) {
        updateUserStatus.execute(id,authUser,dto);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('GESTOR')")
    @PostMapping("/new")
    public ResponseEntity<Void> register(
            @Valid @RequestBody RegisterRequest request,
            HttpServletResponse response
    ) {

        register.execute(request);

//        Cookie cookie = cookieService.createSessionCookie(result.token());
//        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }

}
