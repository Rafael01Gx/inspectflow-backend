package br.com.inspectflow.infrastructure.persistence.bucket;

import br.com.inspectflow.application.http.handlers.MinioOperationException;
import br.com.inspectflow.infrastructure.config.properties.MinioProperties;
import br.com.inspectflow.infrastructure.persistence.bucket.repositories.UploadFileUseCase;
import br.com.inspectflow.infrastructure.utils.MinioObserveUtil;
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
    private final MinioObserveUtil minioObserveUtil;

    @Override
    public String execute(String objectKey, MultipartFile file){
       return minioObserveUtil.observe("upload",minioProperties.bucketName() ,objectKey,()->{
           try {
               internalMinioClient.putObject(
                       PutObjectArgs.builder()
                               .bucket(minioProperties.bucketName())
                               .object(objectKey)
                               .stream(file.getInputStream(), file.getSize(), -1L)
                               .contentType(file.getContentType())
                               .build()
               );
               return objectKey;
           } catch (Exception e) {
               throw new MinioOperationException("Erro durante a tentativa de upload para MinIO", e);
           }
       });
    }
}
