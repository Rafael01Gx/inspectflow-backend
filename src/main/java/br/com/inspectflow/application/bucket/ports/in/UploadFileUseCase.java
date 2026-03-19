package br.com.inspectflow.application.bucket.ports.in;

import br.com.inspectflow.domain.bucket.dto.UploadResponse;
import br.com.inspectflow.domain.equipment.enums.AttachmentType;
import org.springframework.web.multipart.MultipartFile;

public interface UploadFileUseCase {

    UploadResponse execute(String equipmentId, AttachmentType attType, MultipartFile file);
}
