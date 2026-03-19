package br.com.inspectflow.domain.bucket.dto;

import lombok.Builder;

import java.io.InputStream;

@Builder
public record DownloadResponse(
        InputStream file,
        String filename,
        String contentType
) {
}
