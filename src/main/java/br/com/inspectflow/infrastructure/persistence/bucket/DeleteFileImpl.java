package br.com.inspectflow.infrastructure.persistence.bucket;

import br.com.inspectflow.application.http.handlers.MinioOperationException;
import br.com.inspectflow.infrastructure.config.properties.MinioProperties;
import br.com.inspectflow.infrastructure.persistence.bucket.repositories.DeleteFileUseCase;
import br.com.inspectflow.infrastructure.utils.MinioObserveUtil;
import io.minio.MinioClient;
import io.minio.RemoveObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeleteFileImpl implements DeleteFileUseCase {

    private final MinioClient internalMinioClient;
    private final MinioProperties minioProperties;
    private final MinioObserveUtil minioObserveUtil;



    @Override
    public void execute(String fileUrl) {
        minioObserveUtil.observe("delete", minioProperties.bucketName(), fileUrl, () -> {
            try {
                internalMinioClient.removeObject(
                        RemoveObjectArgs.builder()
                                .bucket(minioProperties.bucketName())
                                .object(fileUrl)
                                .build()
                );
            } catch (Exception e) {
                throw new MinioOperationException("Erro ao tentar remover arquivo do MinIO", e);
            }
            return null;
        });
    }
}
