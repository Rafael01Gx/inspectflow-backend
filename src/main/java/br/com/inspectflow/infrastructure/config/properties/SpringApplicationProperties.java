package br.com.inspectflow.infrastructure.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.application")
public record SpringApplicationProperties(
        String name
) {
}
