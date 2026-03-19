package br.com.inspectflow.application.equipment.dto;

import br.com.inspectflow.domain.equipment.enums.AttachmentType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EquipmentAttachmentRequest(
        @NotNull UUID equipmentId,
        @NotNull AttachmentType type,
        @NotNull MultipartFile file
) {
}
