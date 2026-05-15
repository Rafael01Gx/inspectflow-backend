package br.com.inspectflow.domain.bucket.repository;

import br.com.inspectflow.domain.bucket.dto.UploadRequest;
import br.com.inspectflow.domain.equipment.enums.AttachmentType;
import br.com.inspectflow.domain.order.enums.OrderAttachmentType;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

public interface BucketRepository {
    UploadRequest uploadDocFile(String equipmentCode, AttachmentType attachmentType, MultipartFile file);
    UploadRequest uploadOrderDoc(String equipmentCode, OrderAttachmentType orderAttachmentType, MultipartFile file, UUID orderId);
    String uploadImageFile(String equipmentCode,MultipartFile file);

    String getPresignedUrl(String objectKey);
    InputStream getFile(String fileUrl);
    void deleteFile(String fileName);
}
