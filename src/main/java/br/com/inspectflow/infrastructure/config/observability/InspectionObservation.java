package br.com.inspectflow.infrastructure.config.observability;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

/**
 * Métricas de Negócio — Domínio de Inspeções — InspectFlow
 *<p>
 * PROPÓSITO:
 *   Métricas técnicas (latência HTTP, queries SQL) são geradas automaticamente.
 *   Este componente adiciona métricas de NEGÓCIO relevantes para o domínio:
 *   - Quantas inspeções foram criadas/aprovadas/reprovadas?
 *   - Qual o tempo médio de conclusão de uma inspeção?
 *   - Quantos arquivos foram enviados por tipo?
 * <p>
 * COMO USAR:
 *   Injete nas classes de serviço da camada Application:
 *
 *   // application/inspection/services/InspectionService.java
 *   @Service
 *   public class InspectionService {
 *
 *       private final InspectionObservation metrics;
 *
 *       public InspectionDto create(InspectionRequest request) {
 *           return metrics.recordCreation(request.getType(), () -> {
 *               // lógica de negócio aqui
 *               return repository.save(toEntity(request));
 *           });
 *       }
 *
 *       public void approve(String id) {
 *           metrics.incrementStatusChange("approved", "industrial");
 *       }
 *   }
 *
 * DASHBOARDS GRAFANA:
 *   - Painel: "Inspeções por Status" → inspection_status_changes_total
 *   - Painel: "Taxa de Criação"      → inspection_created_total
 *   - Painel: "Latência por Tipo"    → inspection_creation_duration_seconds
 */

@Component
public class InspectionObservation {


    private static final String METRIC_CREATED        = "inspection.created";
    private static final String METRIC_STATUS_CHANGE  = "inspection.status.change";
    private static final String METRIC_FILE_UPLOAD    = "inspection.file.upload";
    private static final String METRIC_CREATION_TIME  = "inspection.creation.duration";
    private static final String OBSERVATION_CREATE    = "inspection.create";

    private final MeterRegistry meterRegistry;
    private final ObservationRegistry observationRegistry;

    public InspectionObservation(MeterRegistry meterRegistry,
                                 ObservationRegistry observationRegistry) {
        this.meterRegistry = meterRegistry;
        this.observationRegistry = observationRegistry;
    }

    /**
     * Criação de Inspeção — com Span + métrica
     * <p>
     * Registra a criação de uma inspeção.
     * Gera um Span no Jaeger + métrica no Prometheus.
     *
     * @param type     Tipo da inspeção (ex: "structural", "electrical", "hydraulic")
     * @param supplier Lógica de negócio que cria a inspeção
     */
    public <T> T recordCreation(String type, Supplier<T> supplier) {
        return Observation.createNotStarted(OBSERVATION_CREATE, observationRegistry)
                .contextualName("criar inspeção")
                .lowCardinalityKeyValue("inspection.type", type)
                .observe(() -> {
                    T result = supplier.get();
                    // Incrementa contador de inspeções criadas por tipo
                    Counter.builder(METRIC_CREATED)
                            .description("Total de inspeções criadas")
                            .tag("type", type)
                            .register(meterRegistry)
                            .increment();
                    return result;
                });
    }


    /**
     *  Mudança de Status
     * <p>
     * Registra uma mudança de status de inspeção.
     *
     * @param status   Novo status (ex: "approved", "rejected", "pending", "in_progress")
     * @param category Categoria da inspeção
     */
    public void incrementStatusChange(String status, String category) {
        Counter.builder(METRIC_STATUS_CHANGE)
                .description("Mudanças de status de inspeções")
                .tag("status",   status)
                .tag("category", category)
                .register(meterRegistry)
                .increment();
    }


    /**
     * Upload de Arquivos de Inspeção
     * <p>
     * Registra o upload de um arquivo de inspeção.
     *
     * @param fileType  Tipo do arquivo (ex: "pdf", "image", "video")
     * @param sizeBytes Tamanho do arquivo em bytes
     */
    public void recordFileUpload(String fileType, long sizeBytes) {
        // Contador de uploads por tipo
        Counter.builder(METRIC_FILE_UPLOAD + ".count")
                .description("Total de arquivos enviados em inspeções")
                .tag("file.type", fileType)
                .register(meterRegistry)
                .increment();

        // Distribuição de tamanhos (histograma)
        meterRegistry.summary(METRIC_FILE_UPLOAD + ".size.bytes",
                        "file.type", fileType)
                .record(sizeBytes);
    }


    /**
     * Timer manual (para quando não usar @Observed).
     * <p>
     * Mede o tempo de execução de uma operação de inspeção.
     * Use quando precisar de controle fino sobre o timer.
     *
     * @param operation Nome da operação (ex: "generate-report", "validate-fields")
     * @param type      Tipo da inspeção
     * @param supplier  Operação a ser medida
     */
    public <T> T recordTimed(String operation, String type, Supplier<T> supplier) {
        Timer timer = Timer.builder(METRIC_CREATION_TIME)
                .description("Duração de operações de inspeção")
                .tag("operation", operation)
                .tag("type",      type)
                .publishPercentiles(0.5, 0.95, 0.99)
                .register(meterRegistry);

        return timer.record(supplier);
    }
}