package br.com.inspectflow.domain.bucket.dto;

public record UploadRequest(
        String fileName,
        String fileUrl
) {
}
