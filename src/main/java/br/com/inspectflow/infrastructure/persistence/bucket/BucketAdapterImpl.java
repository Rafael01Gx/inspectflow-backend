package br.com.inspectflow.infrastructure.persistence.bucket;

import br.com.inspectflow.application.http.handlers.BusinessException;
import br.com.inspectflow.domain.bucket.dto.UploadRequest;
import br.com.inspectflow.domain.bucket.repository.BucketRepository;
import br.com.inspectflow.domain.equipment.enums.AttachmentType;
import br.com.inspectflow.infrastructure.config.properties.MinioProperties;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class BucketAdapterImpl implements BucketRepository {

    private final MinioClient minioClient;
    private final MinioProperties minioProperties;

    @Override
    public UploadRequest uploadDocFile(String equipmentCode, AttachmentType attType, MultipartFile file){
        var fileNameLocalNameUrl= equipmentCode.toLowerCase() +"/"+ attType.getValue().toLowerCase() +"/"+ new Date().getTime();
        var bucketUrlFile = getFileURL(fileNameLocalNameUrl);

       upload(fileNameLocalNameUrl, file);

        return new UploadRequest(equipmentCode.toLowerCase() +"-"+ new Date().getTime(),bucketUrlFile) ;
    }

    @Override
    public String uploadImageFile(String equipmentCode,MultipartFile file){
        var fileNameLocalNameUrl= equipmentCode.toLowerCase() +"/"+ "image" +"/"+ new Date().getTime();
        upload(fileNameLocalNameUrl, file);
        return getFileURL(fileNameLocalNameUrl);
    }

    @Override
    public String getPresignedUrl(String objectKey) {
        if (objectKey == null) return null;
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(minioProperties.bucketName())
                            .object(objectKey.replace(minioProperties.bucketName()+"/", ""))
                            .expiry(2, TimeUnit.HOURS)
                            .build()
            );
        } catch (Exception e) {
            log.error("Error generating presigned URL", e);
            return null;
        }
    }

    @Override
    public InputStream getFile(String fileUrl) {
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(minioProperties.bucketName())
                            .object(getObjectFile(fileUrl))
                            .build()
            );
        } catch (ErrorResponseException e) {
            throw new RuntimeException(e);
        } catch (InsufficientDataException e) {
            throw new RuntimeException(e);
        } catch (InternalException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (InvalidResponseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (ServerException e) {
            throw new RuntimeException(e);
        } catch (XmlParserException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteFile(String fileUrl) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(minioProperties.bucketName())
                            .object(getObjectFile(fileUrl))
                            .build()
            );
        } catch (ErrorResponseException e) {
            throw new RuntimeException(e);
        } catch (InsufficientDataException e) {
            throw new RuntimeException(e);
        } catch (InternalException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (InvalidResponseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (ServerException e) {
            throw new RuntimeException(e);
        } catch (XmlParserException e) {
            throw new RuntimeException(e);
        }
    }


    private void upload(String fileNameLocalNameUrl, MultipartFile file){
        try {
          minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(minioProperties.bucketName())
                            .object(fileNameLocalNameUrl)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
        } catch (ErrorResponseException e) {
            throw new RuntimeException(e);
        } catch (InsufficientDataException e) {
            throw new RuntimeException(e);
        } catch (InternalException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (InvalidResponseException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (ServerException e) {
            throw new RuntimeException(e);
        } catch (XmlParserException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getFileURL(String fileLocalUrl){
        return  minioProperties.bucketName() +
                "/"+ fileLocalUrl;
    }


    private String getObjectFile(String fileLocalNameUrl) {
        if (fileLocalNameUrl == null || !fileLocalNameUrl.contains(minioProperties.bucketName() + "/")) {
            throw new BusinessException("Caminho do arquivo inválido para remoção no MinIO");
        }
        return fileLocalNameUrl.split(minioProperties.bucketName() + "/")[1];
    }
}
