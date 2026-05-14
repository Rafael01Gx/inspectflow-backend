package br.com.inspectflow.infrastructure.scheduling;

import br.com.inspectflow.infrastructure.notification.sse.SseEmitterRegistry;
import br.com.inspectflow.infrastructure.persistence.postgres.notification.PostgresNotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Configuration
@EnableScheduling
@Slf4j
@RequiredArgsConstructor
public class SchedulingConfig {

    private final SseEmitterRegistry registry;
    private final PostgresNotificationRepository notificationRepository;


    @Scheduled(fixedDelay = 30_000)
    public void sendHeartbeat() {
        int connected = registry.connectedCount();
        if (connected > 0) {
            log.debug("Heartbeat SSE — {} conexões ativas", connected);
            registry.sendHeartbeat();
        }
    }

    @Scheduled(cron = "0 0 2 * * *")
    @Transactional
    public void deleteExpiredNotifications() {
        notificationRepository.deleteByExpiresAtBefore(Instant.now());
    }

    @Scheduled(cron = "0 0 2 * * *")
    @Transactional
    public void deleteExpiredReadNotifications() {
        notificationRepository.deleteReadBefore(Instant.now().minus(1, ChronoUnit.DAYS));
    }
}