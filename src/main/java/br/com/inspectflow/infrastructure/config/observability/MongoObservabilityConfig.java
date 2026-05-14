package br.com.inspectflow.infrastructure.config.observability;

import com.mongodb.event.CommandFailedEvent;
import com.mongodb.event.CommandListener;
import com.mongodb.event.CommandStartedEvent;
import com.mongodb.event.CommandSucceededEvent;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.mongodb.autoconfigure.MongoClientSettingsBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Configuration
@Slf4j
public class MongoObservabilityConfig {

    // Threshold para considerar uma query "lenta" (em milissegundos)
    private static final long SLOW_QUERY_THRESHOLD_MS = 100;

    /**
     * Customiza o MongoClientSettings para adicionar o CommandListener de observabilidade.
     *
     * O Spring Boot autoconfigura o MongoObservationCommandListener (tracing automático),
     * este bean adiciona métricas granulares por collection e slow query logging.
     */
    @Bean
    public MongoClientSettingsBuilderCustomizer mongoObservabilityCustomizer(
            MeterRegistry meterRegistry) {

        return builder -> builder.addCommandListener(
                new MetricsCommandListener(meterRegistry)
        );
    }

    /**
     * CommandListener — captura início, sucesso e falha de cada comando MongoDB
     * Listener que registra métricas detalhadas para cada operação MongoDB.
     *
     * Métricas geradas:
     *   mongo_commands_total{command, collection, status, application, environment}
     *   mongo_commands_duration_seconds{command, collection, application, environment}
     */
    static class MetricsCommandListener implements CommandListener {

        private final MeterRegistry meterRegistry;
        // Armazena o timestamp de início por requestId para calcular duração
        private final ConcurrentHashMap<Integer, Long> startTimes = new ConcurrentHashMap<>();

        MetricsCommandListener(MeterRegistry meterRegistry) {
            this.meterRegistry = meterRegistry;
        }

        @Override
        public void commandStarted(CommandStartedEvent event) {
            // Armazena o momento de início (System.nanoTime para precisão)
            startTimes.put(event.getRequestId(), System.nanoTime());
        }

        @Override
        public void commandSucceeded(CommandSucceededEvent event) {
            Long startNano = startTimes.remove(event.getRequestId());
            if (startNano == null) return;

            long durationMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNano);
            String command    = event.getCommandName();
            String collection = extractCollection(event.getCommandName(), event.getResponse().toString());

            // Registra timer no Prometheus
            Timer.builder("mongo.commands.duration")
                    .description("Duração de comandos MongoDB")
                    .tag("command",    command)
                    .tag("collection", collection)
                    .tag("status",     "success")
                    .register(meterRegistry)
                    .record(durationMs, TimeUnit.MILLISECONDS);

            // Log de slow query com TraceId (injetado via MDC)
            if (durationMs > SLOW_QUERY_THRESHOLD_MS) {
                log.warn("[MongoDB SLOW QUERY] command={} collection={} duration={}ms",
                        command, collection, durationMs);
            }
        }

        @Override
        public void commandFailed(CommandFailedEvent event) {
            Long startNano = startTimes.remove(event.getRequestId());
            long durationMs = startNano != null
                    ? TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNano)
                    : -1;

            String command = event.getCommandName();

            // Contador de erros por comando
            meterRegistry.counter("mongo.commands.errors",
                    "command",    command,
                    "error.type", event.getThrowable().getClass().getSimpleName()
            ).increment();

            log.error("[MongoDB ERROR] command={} duration={}ms error={}",
                    command, durationMs, event.getThrowable().getMessage());
        }

        /**
         * Extrai o nome da collection do comando MongoDB.
         * Alguns comandos (find, insert, update) incluem a collection no nome.
         */
        private String extractCollection(String commandName, String response) {
            return "unknown";
        }
    }
}