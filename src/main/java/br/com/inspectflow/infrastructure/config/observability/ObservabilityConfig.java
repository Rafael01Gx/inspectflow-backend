package br.com.inspectflow.infrastructure.config.observability;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.micrometer.metrics.autoconfigure.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class ObservabilityConfig {

    @Value("${spring.application.name:inspectflow}")
    private String applicationName;

    @Value("${ENVIRONMENT:local}")
    private String environment;

    @Value("${BUILD_VERSION:0.0.1-SNAPSHOT}")
    private String version;

    @Bean
    public MeterRegistryCustomizer<MeterRegistry> globalTagsCustomizer() {
        return registry -> registry.config()
                .commonTags(Tags.of(
                        "application", applicationName,
                        "environment", environment,
                        "version",     version
                ));
    }

    @Bean
    public ObservedAspect observedAspect(ObservationRegistry observationRegistry) {
        return new ObservedAspect(observationRegistry);
    }
}
