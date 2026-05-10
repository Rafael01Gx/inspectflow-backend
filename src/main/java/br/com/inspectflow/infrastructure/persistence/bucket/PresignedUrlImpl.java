package br.com.inspectflow.infrastructure.persistence.bucket;

import br.com.inspectflow.infrastructure.config.properties.MinioProperties;
import br.com.inspectflow.infrastructure.config.properties.SpringApplicationProperties;
import br.com.inspectflow.infrastructure.persistence.bucket.repositories.PresignedUrlUseCase;
import br.com.inspectflow.infrastructure.utils.MinioObserveUtil;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.Http;
import io.minio.MinioClient;
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
    private final MinioObserveUtil minioObserveUtil;
    private final SpringApplicationProperties properties;


    @Override
    public String execute(String objectKey) {
        if (objectKey == null) return null;
        return minioObserveUtil.observe("presigned-url", minioProperties.bucketName(), objectKey, () -> {
            try {
                return publicMinioClient.getPresignedObjectUrl(
                        GetPresignedObjectUrlArgs.builder()
                                .method(Http.Method.GET)
                                .bucket(minioProperties.bucketName())
                                .object(objectKey)
                                .expiry(2, TimeUnit.HOURS)
                                .build()
                ).replace("http://", properties.profiles().active().equals("dev") ? "http://" : "https://");
            } catch (Exception e) {
                log.error("Error generating presigned URL", e);
                return null;
            }
        });
    }
}
