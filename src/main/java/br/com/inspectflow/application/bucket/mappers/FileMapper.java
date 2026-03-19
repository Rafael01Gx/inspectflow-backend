package br.com.inspectflow.application.bucket.mappers;

import br.com.inspectflow.domain.bucket.dto.DownloadResponse;
import br.com.inspectflow.domain.equipment.models.EquipmentAttachment;

import java.io.InputStream;

public class FileMapper {

    public static DownloadResponse toResponse(EquipmentAttachment attachment, InputStream file){
        var filename = attachment.getFileName() + "." + fileExtension(attachment.getContentType());
        return DownloadResponse.builder()
                .contentType(attachment.getContentType())
                .filename(filename)
                .file(file)
                .build();
    }


    private static String fileExtension(String contentType){

        return switch (contentType) {
            case "application/pdf" -> "pdf";
            case "application/msword" -> "doc";
            case "application/vnd.openxmlformats-officedocument.wordprocessingml.document" -> "docx";
            default -> throw new IllegalArgumentException("Tipo de arquivo não suportado: " + contentType);
        };
    }
}
