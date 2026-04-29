package br.com.inspectflow.infrastructure.persistence.bucket;

import br.com.inspectflow.application.http.handlers.StorageException;
import br.com.inspectflow.infrastructure.config.properties.MinioProperties;
import br.com.inspectflow.infrastructure.persistence.bucket.repositories.UploadFileUseCase;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
@Slf4j
public class UploadFileImpl implements UploadFileUseCase {

    private final MinioClient internalMinioClient;
    private final MinioProperties minioProperties;

    @Override
    public void execute(String fileNameLocalNameUrl, MultipartFile file){
        try {
            internalMinioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(minioProperties.bucketName())
                            .object(fileNameLocalNameUrl)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
        } catch (Exception e) {
            log.error("Erro durante a tentativa de upload para MinIO", e);
            throw new StorageException("Erro durante a tentativa de upload para MinIO", e);
        }
    }
}
