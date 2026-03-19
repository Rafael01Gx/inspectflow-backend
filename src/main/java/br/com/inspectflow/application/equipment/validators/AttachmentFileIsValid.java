package br.com.inspectflow.application.equipment.validators;

import br.com.inspectflow.application.equipment.dto.EquipmentAttachmentRequest;
import br.com.inspectflow.application.equipment.ports.out.FileDetectedType;
import br.com.inspectflow.application.http.handlers.EquipmentAttachmentException;
import br.com.inspectflow.domain.equipment.repositories.EquipmentAttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AttachmentFileIsValid implements EquipmentValidator<EquipmentAttachmentRequest>{
    private final EquipmentAttachmentRepository repository;
    private final FileDetectedType  fileDetectedType;
    private final List<String> acceptedType = List.of(
            "application/pdf",
            "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
    );

    @Override
    public void execute(EquipmentAttachmentRequest dto) {
        MultipartFile file = dto.file();

       if (file.isEmpty()) error("Arquivo não foi encontrado ou carregado!");
       if (file.getSize() > (10*1024*1024)) error("Arquivo excedeu o limite máximo permitido de 10MB") ;
       if (!fileDetectedType.execute(file,acceptedType)) error("Formato de arquivo inválido!");
       if (repository.existsByEquipmentIdAndType(dto.equipmentId(), dto.type())) error("Já existe um arquivo relacionado a " + dto.type().getValue() + " para este equipamento!");
    }

    private void error(String message){
        throw new EquipmentAttachmentException(message);
    }
}
