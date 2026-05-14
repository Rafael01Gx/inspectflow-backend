package br.com.inspectflow.infrastructure.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.data.mongodb")
public record MongoDBProperties(
        String uri,
        String database
) {
}
