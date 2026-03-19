package br.com.inspectflow.application.equipment.services;

import br.com.inspectflow.application.bucket.services.UploadFileService;
import br.com.inspectflow.application.common.validators.IdConsistencyValidator;
import br.com.inspectflow.application.equipment.dto.EquipmentAttachmentRequest;
import br.com.inspectflow.application.equipment.mappers.AttachmentMapper;
import br.com.inspectflow.application.equipment.ports.in.UploadEquipmentAttachmentUseCase;
import br.com.inspectflow.application.equipment.validators.AttachmentFileIsValid;
import br.com.inspectflow.application.http.handlers.EquipmentNotFoundException;
import br.com.inspectflow.domain.equipment.models.Equipment;
import br.com.inspectflow.domain.equipment.models.EquipmentAttachment;
import br.com.inspectflow.domain.equipment.repositories.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UploadEquipmentAttachment implements UploadEquipmentAttachmentUseCase {

    private final EquipmentRepository repository;
    private final UploadFileService uploadFileService;
    private final AttachmentFileIsValid fileValidator;
    private final IdConsistencyValidator<UUID> idConsistencyValidator;
    @Override
    @Transactional
    public void execute(UUID id, EquipmentAttachmentRequest dto) {

        idConsistencyValidator.execute(id, dto.equipmentId());
        fileValidator.execute(dto);

        Equipment equipment = repository.findById(id).orElseThrow(EquipmentNotFoundException::new);

        var uploadResponse = uploadFileService.execute(equipment.getCode(), dto.type() ,dto.file());

        EquipmentAttachment attachment = AttachmentMapper.toAttachment(dto,uploadResponse);

        equipment.addAttachment(attachment);
        
        repository.save(equipment);

    }
}
