package com.spring.demo.HostOCR;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/ocr")
public class OCRHostController {

    private final OCRHostService ocrHostService;

    @Autowired
    public OCRHostController(OCRHostService ocrHostService) {
        this.ocrHostService = ocrHostService;
    }


    @PostMapping("/host")
    public ResponseEntity<String> uploadAndProcessFile(@RequestParam("file") MultipartFile file) {
        try {

            String uploadDir = System.getProperty("user.dir") + "/uploads";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String filePath = uploadDir + "/" + file.getOriginalFilename();
            File savedFile = new File(filePath);
            file.transferTo(savedFile);


            String response = ocrHostService.processImage(filePath);
            return ResponseEntity.ok(response);

        } catch (IOException e) {
            return ResponseEntity.status(500).body("파일 업로드 실패: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("OCR 처리 실패: " + e.getMessage());
        }
    }
}
