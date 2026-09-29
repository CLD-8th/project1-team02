package com.workboard.workboard.attachment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class AttachmentController {

    private final AttachmentService attachmentService;

    @PostMapping("/notices/{noticeId}/attachments")
    public ResponseEntity<String> upload(@PathVariable Long noticeId,
                                         @RequestParam("file") MultipartFile file) throws IOException {
        String storedKey = attachmentService.store(file);
        return ResponseEntity.ok(storedKey);
    }
}