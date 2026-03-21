package br.com.inspectflow.application.equipment.dto;

import br.com.inspectflow.domain.equipment.enums.AttachmentType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EquipmentAttachmentRequest(

        @NotNull(message = "Id do equipamento é obrigatório")
        UUID equipmentId,

        @NotNull(message = "Tipo do anexo é obrigatório")
        AttachmentType type,

        @NotNull(message = "Arquivo é obrigatório")
        MultipartFile file
) {
}
