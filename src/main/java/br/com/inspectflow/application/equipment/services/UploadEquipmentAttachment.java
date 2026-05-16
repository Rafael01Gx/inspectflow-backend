package br.com.inspectflow.application.equipment.services;

import br.com.inspectflow.application.bucket.services.UploadFileService;
import br.com.inspectflow.application.common.events.RollBackMinio;
import br.com.inspectflow.application.common.events.publisher.RollBackMinioEventPublisher;
import br.com.inspectflow.application.common.validators.IdConsistencyValidator;
import br.com.inspectflow.application.equipment.dto.EquipmentAttachmentRequest;
import br.com.inspectflow.application.equipment.dto.EquipmentDetailsResponse;
import br.com.inspectflow.application.equipment.mappers.AttachmentMapper;
import br.com.inspectflow.application.equipment.ports.in.UploadEquipmentAttachmentUseCase;
import br.com.inspectflow.application.equipment.validators.AttachmentFileIsValid;
import br.com.inspectflow.application.http.handlers.EquipmentNotFoundException;
import br.com.inspectflow.domain.bucket.dto.UploadRequest;
import br.com.inspectflow.domain.equipment.models.Equipment;
import br.com.inspectflow.domain.equipment.models.EquipmentAttachment;
import br.com.inspectflow.domain.equipment.repositories.EquipmentRepository;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UploadEquipmentAttachment implements UploadEquipmentAttachmentUseCase {

    private final EquipmentRepository repository;
    private final UploadFileService uploadFileService;
    private final AttachmentFileIsValid fileValidator;
    private final IdConsistencyValidator<UUID> idConsistencyValidator;
    private final RollBackMinioEventPublisher publisher;


    @Override
    @Transactional
    @Observed(name = "equipment.attachment-upload",
            contextualName = "Adiciona anexos de um equipamento")
    public EquipmentDetailsResponse execute(UUID id, EquipmentAttachmentRequest dto) {
        idConsistencyValidator.execute(id, dto.equipmentId());
        fileValidator.execute(dto);

        Equipment equipment = repository.findById(id).orElseThrow(EquipmentNotFoundException::new);

        UploadRequest uploadResponse = uploadFileService.execute(equipment.getCode(), dto.type(), dto.file());


        EquipmentAttachment attachment = AttachmentMapper.toAttachment(dto, uploadResponse);

        equipment.addAttachment(attachment);

        publisher.publishRollBackMinio(new RollBackMinio(uploadResponse.fileUrl()));

        repository.save(equipment);

        return EquipmentDetailsResponse.from(equipment);

    }
}
