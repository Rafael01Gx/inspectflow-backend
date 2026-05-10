package br.com.inspectflow.infrastructure.config.observability;

import io.opentelemetry.exporter.otlp.http.trace.OtlpHttpSpanExporter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class OtlpExporterConfig {


    @Value("${management.otlp.tracing.endpoint:http://127.0.0.1:4318/v1/traces}")
    private String endpoint;

    @Bean
    @ConditionalOnMissingBean(OtlpHttpSpanExporter.class)
    public OtlpHttpSpanExporter otlpHttpSpanExporter() {
        log.info(">>> Criando OtlpHttpSpanExporter manualmente para: {}", endpoint);
        return OtlpHttpSpanExporter.builder()
                .setEndpoint(endpoint)
                .build();
    }
}