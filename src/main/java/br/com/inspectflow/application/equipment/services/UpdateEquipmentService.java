package br.com.inspectflow.application.equipment.services;

import br.com.inspectflow.application.bucket.services.DeleteFileService;
import br.com.inspectflow.application.bucket.services.UploadFileService;
import br.com.inspectflow.application.checklist.services.ChecklistSyncService;
import br.com.inspectflow.application.common.validators.IdConsistencyValidator;
import br.com.inspectflow.application.equipment.dto.EquipmentResponse;
import br.com.inspectflow.application.equipment.dto.UpdateEquipmentRequest;
import br.com.inspectflow.application.equipment.mappers.EquipmentMapper;
import br.com.inspectflow.application.equipment.ports.in.UpdateEquipmentUseCase;
import br.com.inspectflow.application.equipment.validators.AttachmentFileIsValid;
import br.com.inspectflow.application.http.handlers.EquipmentNotFoundException;
import br.com.inspectflow.domain.equipment.models.Equipment;
import br.com.inspectflow.domain.equipment.repositories.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateEquipmentService implements UpdateEquipmentUseCase {
    private final EquipmentRepository repository;
    private final IdConsistencyValidator<UUID> idValidator;
    private final ChecklistSyncService checklistSyncService;
    private final AttachmentFileIsValid fileValidator;
    private final UploadFileService uploadFileService;
    private final DeleteFileService deleteFileService;

    @Override
    @Transactional
    public EquipmentResponse execute(UUID id, UpdateEquipmentRequest dto, MultipartFile file) {
       idValidator.execute(id,dto.id());

       var equipment = repository.findById(id).orElseThrow(EquipmentNotFoundException::new);

        if (file != null && !file.isEmpty()) {
            fileValidator.execute(file);
            var imageUrl = uploadFileService.execute(equipment.getCode(),file);
            try {
                Optional.ofNullable(equipment.getImageUrl()).ifPresent(deleteFileService::deleteFile);
                equipment.setImageUrl(imageUrl);
            } catch (Exception e) {
                log.error("Erro ao deletar imagem antiga do MinIO");
                deleteFileService.deleteFile(imageUrl);
            }
        }
        EquipmentMapper.fromUpdateDto(equipment,dto);

        Equipment savedEquipment = repository.save(equipment);


        String checklistId = checklistSyncService.syncFromEquipment(savedEquipment);

        if (savedEquipment.getChecklistId() == null) {
             savedEquipment.setChecklistId(checklistId);
        }

        return EquipmentResponse.from(savedEquipment);
    }
}
