package br.com.inspectflow.infrastructure.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.bucket.minio")
public record MinioProperties(
        String bucketName,
        String endpoint,
        String accessKey,
        String secretKey
) {
}
