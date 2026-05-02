package br.com.inspectflow.adapters.in.web.notifications.controller;

import br.com.inspectflow.application.notification.dto.NotificationDto;
import br.com.inspectflow.application.notification.dto.SendNotificationDto;
import br.com.inspectflow.application.notification.dto.SendNotificationRequest;
import br.com.inspectflow.application.notification.ports.in.QueryNotificationUseCase;
import br.com.inspectflow.application.notification.ports.in.SendNotificationUseCase;
import br.com.inspectflow.application.user.services.SecurityUser;
import br.com.inspectflow.infrastructure.notification.sse.SseEmitterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final SseEmitterRegistry registry;
    private final SendNotificationUseCase sendUseCase;
    private final QueryNotificationUseCase queryUseCase;

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter stream(Authentication authentication) {
        UUID userId = extractUserId(authentication);
        SseEmitter emitter = registry.register(userId);

        try {
            long unreadCount = queryUseCase.countUnread(userId);
            emitter.send(SseEmitter.event()
                    .name("connected")
                    .data("{\"unreadCount\":" + unreadCount + "}",
                            MediaType.APPLICATION_JSON));
        } catch (IOException e) {
            emitter.completeWithError(e);
        }

        return emitter;
    }

    // ── Queries ──────────────────────────────────────────────────────────

    @GetMapping("/unread")
    public List<NotificationDto> getUnread(@AuthenticationPrincipal SecurityUser user) {
        return queryUseCase.getUnread(user.getId());
    }

    @GetMapping
    public List<NotificationDto> getAll(
            @AuthenticationPrincipal SecurityUser user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return queryUseCase.getAll(user.getId(), page, size);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countUnread(@AuthenticationPrincipal SecurityUser user) {
        return ResponseEntity.ok(queryUseCase.countUnread(user.getId()));
    }

    // ── Commands ─────────────────────────────────────────────────────────

    @PatchMapping("/{id}/read")
    public ResponseEntity<Void> markAsRead(
            @PathVariable UUID id,
            @AuthenticationPrincipal SecurityUser user) {
        queryUseCase.markAsRead(id, user.getId());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/read-all")
    public ResponseEntity<Void> markAllAsRead(@AuthenticationPrincipal SecurityUser user) {
        queryUseCase.markAllAsRead(user.getId());
        return ResponseEntity.noContent().build();
    }

    // ── Send (uso interno / admin) ────────────────────────────────────────

    @PostMapping("/user/{userId}")
    public ResponseEntity<Void> sendToUser(
            @PathVariable UUID userId,
            @RequestBody SendNotificationRequest request) {
        sendUseCase.sendToUser(new SendNotificationDto(
                userId, request.type(), request.title(),
                request.message(), request.metadata(), request.expiresAt()));
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/group/{groupId}")
    public ResponseEntity<Void> sendToGroup(
            @PathVariable UUID groupId,
            @RequestBody SendNotificationRequest request) {
        sendUseCase.sendToGroup(groupId, new SendNotificationDto(
                null, request.type(), request.title(),
                request.message(), request.metadata(), request.expiresAt()));
        return ResponseEntity.accepted().build();
    }

    // ── Helpers ───────────────────────────────────────────────────────────

    private UUID extractUserId(Authentication authentication) {
        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            String userId = jwtAuth.getToken().getClaimAsString("userId");
            return UUID.fromString(userId);
        }
        throw new IllegalStateException("Tipo de autenticação não suportado: "
                + authentication.getClass());
    }

}