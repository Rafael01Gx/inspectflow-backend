package br.com.inspectflow.domain.bucket.repository;

import br.com.inspectflow.domain.bucket.dto.UploadRequest;
import br.com.inspectflow.domain.equipment.enums.AttachmentType;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface BucketRepository {
    UploadRequest uploadFile(String equipmentCode, AttachmentType attType, MultipartFile file);
    InputStream getFile(String fileUrl);
    void deleteFile(String fileName);
}
