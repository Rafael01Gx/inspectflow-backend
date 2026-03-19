package br.com.inspectflow.domain.bucket.dto;

public record UploadResponse(
        String fileName,
        String fileUrl
) {
}
