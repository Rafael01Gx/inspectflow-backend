package br.com.inspectflow.application.equipment.services;

import br.com.inspectflow.application.bucket.services.UploadFileService;
import br.com.inspectflow.application.checklist.services.ChecklistSyncService;
import br.com.inspectflow.application.equipment.dto.CreateEquipmentRequest;
import br.com.inspectflow.application.equipment.dto.EquipmentResponse;
import br.com.inspectflow.application.equipment.mappers.EquipmentMapper;
import br.com.inspectflow.application.equipment.ports.in.CreateEquipmentUseCase;
import br.com.inspectflow.application.equipment.validators.AttachmentFileIsValid;
import br.com.inspectflow.application.equipment.validators.UniqueEquipmentCodeValidation;
import br.com.inspectflow.domain.equipment.models.Equipment;
import br.com.inspectflow.domain.equipment.repositories.EquipmentRepository;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CreateEquipmentService implements CreateEquipmentUseCase {

    private final EquipmentRepository repository;
    private final UniqueEquipmentCodeValidation validation;
    private final ChecklistSyncService checklistSyncService;
    private final AttachmentFileIsValid fileValidator;
    private final UploadFileService uploadFileService;


    @Override
    @Transactional
    @Observed(name = "equipment.create",
            contextualName = "Cria novo equipamento")
    @CacheEvict(value = "findAllEquipmentList", allEntries = true)
    public EquipmentResponse execute(CreateEquipmentRequest dto, MultipartFile file) {

        validation.execute(dto.code());

        Equipment equipment = EquipmentMapper.fromCreateDto(dto);

        Equipment savedEquipment = repository.save(equipment);

        if (file != null && !file.isEmpty()) {
            fileValidator.execute(file);
            var imageUrl = uploadFileService.execute(dto.code(),file);
            savedEquipment.setImageUrl(imageUrl);
        }

        String checklistId = checklistSyncService.syncFromEquipment(savedEquipment);

        savedEquipment.setChecklistId(checklistId);
        savedEquipment = repository.save(savedEquipment);

        return EquipmentResponse.from(savedEquipment);
    }

}
