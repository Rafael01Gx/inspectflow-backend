package br.com.inspectflow.application.bucket.ports.in;

import br.com.inspectflow.domain.bucket.dto.UploadRequest;
import br.com.inspectflow.domain.equipment.enums.AttachmentType;
import br.com.inspectflow.domain.order.enums.OrderAttachmentType;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface UploadFileUseCase {

    UploadRequest execute(String equipmentId, AttachmentType attType, MultipartFile file);
    UploadRequest execute(String equipmentId, OrderAttachmentType attType, MultipartFile file, UUID orderId);

    String execute(String equipmentId, MultipartFile file);
}
