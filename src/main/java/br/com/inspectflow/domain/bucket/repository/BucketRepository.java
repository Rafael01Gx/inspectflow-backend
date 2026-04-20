package br.com.inspectflow.domain.bucket.repository;

import br.com.inspectflow.domain.bucket.dto.UploadRequest;
import br.com.inspectflow.domain.equipment.enums.AttachmentType;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface BucketRepository {
    UploadRequest uploadDocFile(String equipmentCode, AttachmentType attType, MultipartFile file);
    String uploadImageFile(String equipmentCode,MultipartFile file);

    String getPresignedUrl(String objectKey);
    InputStream getFile(String fileUrl);
    void deleteFile(String fileName);
}
