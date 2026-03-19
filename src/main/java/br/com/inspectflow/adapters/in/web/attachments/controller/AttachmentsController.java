package br.com.inspectflow.adapters.in.web.attachments.controller;

import br.com.inspectflow.application.bucket.services.DownloadFileService;
import br.com.inspectflow.domain.bucket.dto.DownloadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.UUID;

@RestController
@RequestMapping("/attachments")
@RequiredArgsConstructor
public class AttachmentsController {
        private final DownloadFileService downloadFileService;



    @GetMapping("{id}/download")
    public ResponseEntity<StreamingResponseBody> downloadAttachment(@PathVariable UUID id) {
        DownloadResponse req = downloadFileService.execute(id);

    StreamingResponseBody responseBody = outputStream -> {
        try (var fileStream = req.file()) {
            fileStream.transferTo(outputStream);
        }
    };

    return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + req.filename() + "\"")
            .contentType(MediaType.parseMediaType(req.contentType()))
            .body(responseBody);


    }

    @GetMapping("{id}/view")
    public ResponseEntity<StreamingResponseBody> viewAttachment(@PathVariable UUID id) {
        DownloadResponse req = downloadFileService.execute(id);

        StreamingResponseBody responseBody = outputStream -> {
            try (var fileStream = req.file()) {
                fileStream.transferTo(outputStream);
            }
        };

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + req.filename() + "\"")
                .contentType(MediaType.parseMediaType(req.contentType()))
                .body(responseBody);
    }
}