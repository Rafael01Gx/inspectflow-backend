package br.com.inspectflow.adapters.in.web.user.controller;

import br.com.inspectflow.adapters.in.mappers.UserMapper;
import br.com.inspectflow.application.auth.dto.RegisterRequest;
import br.com.inspectflow.application.auth.ports.in.RegisterUseCase;
import br.com.inspectflow.application.user.dto.UserResponse;
import br.com.inspectflow.application.user.ports.in.FindAllUsersUseCase;
import br.com.inspectflow.application.user.services.SecurityUser;
import br.com.inspectflow.domain.common.pagination.PageRequest;
import br.com.inspectflow.domain.common.pagination.PagedResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final FindAllUsersUseCase findAllUsers;
    private final RegisterUseCase register;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(@AuthenticationPrincipal SecurityUser user) {
        return ResponseEntity.ok(UserMapper.toUserResponse(user));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<PagedResponse<UserResponse>> all(@PageableDefault Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),null, "DESC");
        return ResponseEntity.ok(findAllUsers.execute(pageRequest));
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
