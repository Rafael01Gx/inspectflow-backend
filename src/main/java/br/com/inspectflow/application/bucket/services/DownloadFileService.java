package br.com.inspectflow.application.bucket.services;

import br.com.inspectflow.application.bucket.mappers.FileMapper;
import br.com.inspectflow.application.bucket.ports.in.DownloadFileUseCase;
import br.com.inspectflow.application.http.handlers.EquipmentAttachmentException;
import br.com.inspectflow.domain.bucket.dto.DownloadResponse;
import br.com.inspectflow.domain.bucket.repository.BucketRepository;
import br.com.inspectflow.domain.equipment.models.EquipmentAttachment;
import br.com.inspectflow.domain.equipment.repositories.EquipmentAttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DownloadFileService implements DownloadFileUseCase {

    private final BucketRepository bucketRepository;
    private final EquipmentAttachmentRepository equipmentAttachmentRepository;

    @Override
    public DownloadResponse execute(UUID id) {
        EquipmentAttachment attachment = equipmentAttachmentRepository.findById(id).orElseThrow(()-> new EquipmentAttachmentException("Arquivo não encontrado!"));


        InputStream file = bucketRepository.getFile(attachment.getFileUrl());

        return FileMapper.toResponse(attachment, file);
    }
}
