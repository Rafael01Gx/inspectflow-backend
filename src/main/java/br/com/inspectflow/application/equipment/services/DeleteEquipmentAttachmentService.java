package br.com.inspectflow.application.equipment.services;

import br.com.inspectflow.application.bucket.services.DeleteFileService;
import br.com.inspectflow.application.equipment.ports.in.DeleteEquipmentAttachmentUseCase;
import br.com.inspectflow.application.http.handlers.EquipmentAttachmentException;
import br.com.inspectflow.application.http.handlers.EquipmentComponentNotFoundExceprion;
import br.com.inspectflow.domain.equipment.models.Equipment;
import br.com.inspectflow.domain.equipment.models.EquipmentAttachment;
import br.com.inspectflow.domain.equipment.repositories.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteEquipmentAttachmentService implements DeleteEquipmentAttachmentUseCase {
    private final EquipmentRepository equipmentRepository;
    private final DeleteFileService deleteFileService;


    @Override
    @Transactional
    public void execute(UUID equipmentId, UUID attachmentId) {
      Equipment equipment =  equipmentRepository.findById(equipmentId).orElseThrow(EquipmentComponentNotFoundExceprion::new);


      EquipmentAttachment att =  equipment.getAttachments().stream()
              .filter(attachment -> attachment.getId().equals(attachmentId))
              .findFirst()
              .orElseThrow(()-> new EquipmentAttachmentException("O anexo não foi encontrado"));

      equipment.removeAttachment(att);

      deleteFileService.deleteFile(att.getFileUrl());

      equipmentRepository.save(equipment);

    }
}
