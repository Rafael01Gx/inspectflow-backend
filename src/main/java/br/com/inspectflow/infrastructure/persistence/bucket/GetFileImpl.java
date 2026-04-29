package br.com.inspectflow.infrastructure.persistence.bucket;

import br.com.inspectflow.application.http.handlers.StorageException;
import br.com.inspectflow.infrastructure.config.properties.MinioProperties;
import br.com.inspectflow.infrastructure.persistence.bucket.repositories.GetFileUseCase;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetFileImpl implements GetFileUseCase {

    private final MinioClient internalMinioClient;
    private final MinioProperties minioProperties;

    @Override
    public InputStream execute(String fileUrl) {
        try {
            return internalMinioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(minioProperties.bucketName())
                            .object(fileUrl)
                            .build()
            );
        } catch (Exception e) {
            log.error("Erro ao obter arquivo do MinIO", e);
            throw new StorageException("Erro ao obter arquivo do MinIO", e);
        }
    }
}
