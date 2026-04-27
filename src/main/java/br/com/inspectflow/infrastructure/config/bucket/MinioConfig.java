package br.com.inspectflow.infrastructure.config.bucket;

import br.com.inspectflow.infrastructure.config.properties.MinioProperties;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MinioConfig {

    private final MinioProperties properties;

    @Bean(name = "internalMinioClient")
    public MinioClient internalMinioClient() {
        return MinioClient.builder()
                .endpoint(properties.endpoint()) // http://minio:9000
                .credentials(properties.accessKey(), properties.secretKey())
                .build();
    }

    // 🔹 Client público (pre-signed URL)
    @Bean(name = "publicMinioClient")
    public MinioClient publicMinioClient() {
        return MinioClient.builder()
                .endpoint(properties.publicUrl())
                .credentials(properties.accessKey(), properties.secretKey())
                .build();
    }
}
