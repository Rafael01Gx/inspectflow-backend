package br.com.inspectflow.application.equipment.dto;

import br.com.inspectflow.domain.equipment.enums.AttachmentType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EquipmentAttachmentRequest(
        @NotNull AttachmentType type,
        @NotNull MultipartFile file
) {
}
