package br.com.inspectflow.application.bucket.services;

import br.com.inspectflow.application.bucket.ports.in.UploadFileUseCase;
import br.com.inspectflow.domain.bucket.dto.UploadRequest;
import br.com.inspectflow.domain.bucket.repository.BucketRepository;
import br.com.inspectflow.domain.equipment.enums.AttachmentType;
import br.com.inspectflow.domain.order.enums.OrderAttachmentType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UploadFileService implements UploadFileUseCase {

    private final BucketRepository repository;


    @Override
    public UploadRequest execute(String equipmentCode, AttachmentType attachmentType, MultipartFile file) {

       return repository.uploadDocFile(equipmentCode, attachmentType,file);
    }

    @Override
    public UploadRequest execute(String equipmentCode, OrderAttachmentType orderAttachmentType, MultipartFile file , UUID orderId) {

        return repository.uploadOrderDoc(equipmentCode, orderAttachmentType ,file , orderId );
    }

    @Override
    public String execute(String equipmentCode, MultipartFile file) {

       return repository.uploadImageFile(equipmentCode,file);
    }
}
