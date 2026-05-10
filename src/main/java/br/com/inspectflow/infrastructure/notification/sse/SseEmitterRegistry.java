package br.com.inspectflow.infrastructure.notification.sse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@Slf4j
public class SseEmitterRegistry {

    private static final long EMITTER_TIMEOUT_MS = 180_000L;

    private final Map<UUID, CopyOnWriteArrayList<SseEmitter>> userEmitters = new ConcurrentHashMap<>();

    public SseEmitter register(UUID userId) {
        SseEmitter emitter = new SseEmitter(EMITTER_TIMEOUT_MS);

        userEmitters.computeIfAbsent(userId, k -> new CopyOnWriteArrayList<>()).add(emitter);
        log.debug("SSE registrado para userId={}, total conexões={}", userId, connectedCount());

        Runnable cleanup = () -> {removeEmitter(userId, emitter);
            try {
                emitter.complete();
            } catch (Exception e) {
            }
        };
        emitter.onCompletion(cleanup);
        emitter.onTimeout(cleanup);
        emitter.onError(e -> {
            log.debug("SSE erro para userId={}: {}", userId, e.getMessage());
            cleanup.run();
        });

        return emitter;
    }

    public void sendToUser(UUID userId, Object payload) {
        List<SseEmitter> emitters = userEmitters.getOrDefault(userId, new CopyOnWriteArrayList<>());
        if (emitters.isEmpty()) {
            log.debug("Nenhum SSE conectado para userId={}, notificação apenas persistida", userId);
            return;
        }

        List<SseEmitter> dead = new ArrayList<>();
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event()
                        .id(UUID.randomUUID().toString())
                        .name("notification")
                        .data(payload, MediaType.APPLICATION_JSON));
            } catch (IOException | IllegalStateException e) {
                log.debug("SSE morto detectado para userId={}, removendo", userId);
                dead.add(emitter);
            }
        }
        emitters.removeAll(dead);
    }

    public void sendHeartbeat() {
        userEmitters.forEach((userId, emitters) -> {
            List<SseEmitter> dead = new ArrayList<>();
            for (SseEmitter emitter : emitters) {
                try {
                    emitter.send(SseEmitter.event().name("heartbeat").data("ping"));
                } catch (IOException | IllegalStateException e) {
                    // Conexão fechada pelo cliente — remove silenciosamente
                    dead.add(emitter);
                }
            }
            emitters.removeAll(dead);
        });
    }

    public int connectedCount() {
        return userEmitters.values().stream().mapToInt(List::size).sum();
    }

    private void removeEmitter(UUID userId, SseEmitter emitter) {
        CopyOnWriteArrayList<SseEmitter> list = userEmitters.get(userId);
        if (list != null) {
            list.remove(emitter);
            if (list.isEmpty()) {
                userEmitters.remove(userId);
                log.debug("Todas as conexões SSE removidas para userId={}", userId);
            }
        }
    }
}