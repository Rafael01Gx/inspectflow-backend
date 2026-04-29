package br.com.inspectflow.infrastructure.persistence.bucket;

import br.com.inspectflow.infrastructure.config.properties.MinioProperties;
import br.com.inspectflow.infrastructure.persistence.bucket.repositories.PresignedUrlUseCase;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Component
public class PresignedUrlImpl implements PresignedUrlUseCase {

    private final MinioClient publicMinioClient;
    private final MinioProperties minioProperties;

    @Override
    public String execute(String objectKey) {
        if (objectKey == null) return null;
        try {
            return publicMinioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(minioProperties.bucketName())
                            .object(objectKey)
                            .expiry(2, TimeUnit.HOURS)
                            .build()
            );
        } catch (Exception e) {
            log.error("Error generating presigned URL", e);
            return null;
        }
    }
}
