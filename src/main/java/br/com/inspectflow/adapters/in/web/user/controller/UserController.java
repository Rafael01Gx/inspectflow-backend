package br.com.inspectflow.adapters.in.web.user.controller;

import br.com.inspectflow.adapters.in.mappers.PageableRequestMapper;
import br.com.inspectflow.adapters.in.mappers.UserMapper;
import br.com.inspectflow.application.user.dto.*;
import br.com.inspectflow.application.user.ports.in.*;
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

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final FindAllUsersUseCase findAllUsers;
    private final UpdateUserUseCase updateUser;
    private final SearchByNameUseCase searchByName;
    private final UpdateUserRoleUseCase updateUserRole;
    private final UpdateUserStatusUseCase updateUserStatus;
    private final CreateUserUseCase register;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(@AuthenticationPrincipal SecurityUser user) {
        return ResponseEntity.ok(UserMapper.toUserResponse(user));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<PagedResponse<UserResponse>> all(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(findAllUsers.execute(PageableRequestMapper.fromRequest(pageable)));
    }

    @PreAuthorize("hasRole('LIDER')")
    @GetMapping("/search")
    public ResponseEntity<List<UserResponse>> search(@RequestParam String name) {
        return ResponseEntity.ok(searchByName.execute(name));
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
            @Valid @RequestBody CreateUserRequest request,
            HttpServletResponse response
    ) {

        register.execute(request);

        return ResponseEntity.ok().build();
    }

}
