package com.workboard.workboard.attachment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class AttachmentService {

    private final Path uploadDir;

    public AttachmentService(@Value("${app.upload-dir}") String uploadDir) throws IOException {
        this.uploadDir = Paths.get(uploadDir);
        Files.createDirectories(this.uploadDir);
    }

    // 저장 후 저장 키(파일명)를 반환 → DB attachments.stored_key 컬럼에 기록
    public String store(MultipartFile file) throws IOException {
        String storedKey = UUID.randomUUID() + "_" + file.getOriginalFilename();
        file.transferTo(uploadDir.resolve(storedKey));
        return storedKey;
    }
}