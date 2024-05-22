package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
public class ImageController {

    //@Value 어노테이션을 넣어 파일을 저장할 디렉토리 경로를 application.properties에서 읽어옴
    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostMapping("/api/image")
    public String handleImageUpload(@RequestParam("image") MultipartFile image) {
        if (!image.isEmpty()) {
            try {
                // 파일 저장
                Path copyLocation = Paths.get(uploadDir + image.getOriginalFilename());
                Files.copy(image.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);

                // 파일 URL 반환
                return "파일 업로드 성공: " + image.getOriginalFilename() +
                        "\n파일에 접근하려면 다음 URL을 사용하세요: " +
                        "http://192.168.101.81:8080/api/images/" + image.getOriginalFilename();
            } catch (Exception e) {
                return "이미지 업로드 실패: " + e.getMessage();
            }
        } else {
            return "이미지 선택 안됨";
        }
    }

    @GetMapping("/api/images/{filename:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .header("Content-Disposition", "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
