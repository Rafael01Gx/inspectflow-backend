package br.com.inspectflow.adapters.in.web.notifications.controller;

import br.com.inspectflow.adapters.in.helpers.ExtractUserId;
import br.com.inspectflow.application.notification.dto.NotificationDto;
import br.com.inspectflow.application.notification.ports.in.QueryNotificationUseCase;
import br.com.inspectflow.infrastructure.notification.sse.SseEmitterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    private final QueryNotificationUseCase queryUseCase;

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter stream(Authentication authentication) {
        UUID userId = ExtractUserId.fromAuthentication(authentication);
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
    public List<NotificationDto> getUnread(Authentication authentication) {
        UUID userId = ExtractUserId.fromAuthentication(authentication);
        return queryUseCase.getUnread(userId);
    }

    @GetMapping
    public List<NotificationDto> getAll(
            Authentication authentication
           ) {
        UUID userId = ExtractUserId.fromAuthentication(authentication);
        return queryUseCase.getAll(userId);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countUnread(Authentication authentication) {
        UUID userId = ExtractUserId.fromAuthentication(authentication);
        return ResponseEntity.ok(queryUseCase.countUnread(userId));
    }

    // ── Commands ─────────────────────────────────────────────────────────

    @PatchMapping("/{id}/read")
    public ResponseEntity<Void> markAsRead(
            @PathVariable UUID id,Authentication authentication) {
        UUID userId = ExtractUserId.fromAuthentication(authentication);
        queryUseCase.markAsRead(id, userId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/read-all")
    public ResponseEntity<Void> markAllAsRead(Authentication authentication) {
        UUID userId = ExtractUserId.fromAuthentication(authentication);
        queryUseCase.markAllAsRead(userId);
        return ResponseEntity.noContent().build();
    }



}