package br.com.inspectflow.infrastructure.config.async;

import io.micrometer.context.ContextExecutorService;
import io.micrometer.context.ContextSnapshotFactory;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "asyncExecutor")
    @Primary
    public Executor asyncExecutor(ObservationRegistry observationRegistry) {

        ThreadFactory virtualFactory = Thread.ofVirtual()
                .name("inspectflow-async-", 0)
                .factory();

        ExecutorService delegate = Executors.newThreadPerTaskExecutor(virtualFactory);

        return ContextExecutorService.wrap(            // propagação de contexto
                delegate,
                ContextSnapshotFactory.builder()
                        .build()::captureAll
        );
    }

    @Bean(name = "notificationExecutor")
    public Executor notificationExecutor(ObservationRegistry observationRegistry) {

        ThreadFactory virtualFactory = Thread.ofVirtual()
                .name("inspectflow-notification-", 0)
                .factory();

        ExecutorService delegate = Executors.newThreadPerTaskExecutor(virtualFactory);

        return ContextExecutorService.wrap(
                delegate,
                ContextSnapshotFactory.builder()
                        .build()::captureAll
        );
    }
}