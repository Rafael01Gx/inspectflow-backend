package br.com.inspectflow.application.order.services;

import br.com.inspectflow.application.bucket.mappers.FileMapper;
import br.com.inspectflow.application.http.handlers.EquipmentAttachmentException;
import br.com.inspectflow.application.order.ports.in.DownloadOrderDocumentUseCase;
import br.com.inspectflow.domain.bucket.dto.DownloadResponse;
import br.com.inspectflow.domain.bucket.repository.BucketRepository;
import br.com.inspectflow.domain.order.models.OrderAttachment;
import br.com.inspectflow.domain.order.repositories.OrderAttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DownloadOrderDocumentService implements DownloadOrderDocumentUseCase {

    private final BucketRepository bucketRepository;
    private final OrderAttachmentRepository repository;

    @Override
    public DownloadResponse execute(UUID id) {
        OrderAttachment attachment = repository.findById(id).orElseThrow(()-> new EquipmentAttachmentException("Arquivo não encontrado!"));

        InputStream file = bucketRepository.getFile(attachment.getFileUrl());

        return FileMapper.toResponse(attachment, file);
    }
}
