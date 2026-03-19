package br.com.inspectflow.application.bucket.services;

import br.com.inspectflow.application.bucket.ports.in.UploadFileUseCase;
import br.com.inspectflow.domain.bucket.dto.UploadRequest;
import br.com.inspectflow.domain.bucket.repository.BucketRepository;
import br.com.inspectflow.domain.equipment.enums.AttachmentType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UploadFileService implements UploadFileUseCase {

    private final BucketRepository repository;


    @Override
    public UploadRequest execute(String equipmentCode, AttachmentType attType, MultipartFile file) {

       return repository.uploadFile(equipmentCode, attType,file);
    }
}
