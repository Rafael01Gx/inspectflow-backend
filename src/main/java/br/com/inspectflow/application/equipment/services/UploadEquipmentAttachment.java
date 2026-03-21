package br.com.inspectflow.application.equipment.services;

import br.com.inspectflow.application.bucket.services.DeleteFileService;
import br.com.inspectflow.application.bucket.services.UploadFileService;
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
    private final DeleteFileService deleteFileService;
    private final AttachmentFileIsValid fileValidator;
    private final IdConsistencyValidator<UUID> idConsistencyValidator;

    @Override
    @Transactional
    public EquipmentDetailsResponse execute(UUID id, EquipmentAttachmentRequest dto) {
        idConsistencyValidator.execute(id, dto.equipmentId());
        fileValidator.execute(dto);

        Equipment equipment = repository.findById(id).orElseThrow(EquipmentNotFoundException::new);

        UploadRequest uploadResponse = uploadFileService.execute(equipment.getCode(), dto.type(), dto.file());

        try {
            EquipmentAttachment attachment = AttachmentMapper.toAttachment(dto, uploadResponse);
            equipment.addAttachment(attachment);
            repository.saveAndFlush(equipment);

            return EquipmentDetailsResponse.from(equipment);

        } catch (Exception e) {
            try {
                log.warn("Erro ao salvar anexo no banco. Iniciando compensação no MinIO para o arquivo: {}", uploadResponse.fileUrl());
                deleteFileService.deleteFile(uploadResponse.fileUrl());
                log.info("Reversão bem-sucedida. Arquivo removido do MinIO.");
            } catch (Exception deleteEx) {
                log.error("FALHA NA REVERSÃO DO PROCESSAMENTO! Arquivo órfão no MinIO: {}", uploadResponse.fileUrl(), deleteEx);
            }
            throw e;
        }
    }
}
