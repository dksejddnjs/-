package com.example.demo.controller;

import com.example.demo.handler.ChatHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
public class ImageController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final ChatHandler chatHandler;

    public ImageController(ChatHandler chatHandler) {
        this.chatHandler = chatHandler;
    }

    @PostMapping("/api/image")
    public ResponseEntity<String> handleImageUpload(@RequestParam("image") MultipartFile image) {
        if (!image.isEmpty()) {
            try {
                Path copyLocation = Paths.get(uploadDir).resolve(image.getOriginalFilename());
                Files.copy(image.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);

                String fileDownloadUri = "http://192.168.101.81:8080/api/images/" + image.getOriginalFilename();
                return ResponseEntity.ok(fileDownloadUri);

            } catch (Exception e) {
                return ResponseEntity.status(500).body("이미지 업로드 실패: " + e.getMessage());
            }
        } else {
            return ResponseEntity.badRequest().body("이미지 선택 안됨");
        }
    }

    @GetMapping("/api/images/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                String contentType = Files.probeContentType(filePath);
                if (contentType == null) {
                    contentType = "application/octet-stream";
                }

                // 이미지 파일일 경우 브라우저에서 바로 표시
                if (contentType.startsWith("image/")) {
                    return ResponseEntity.ok()
                            .contentType(org.springframework.http.MediaType.parseMediaType(contentType))
                            .body(resource);
                } else { // 이미지 파일이 아닌 경우 다운로드
                    return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                            .contentType(org.springframework.http.MediaType.parseMediaType(contentType))
                            .body(resource);
                }
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
