package com.spring.demo.HostOCR;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/ocr")
public class OCRHostController {

    private final OCRHostService ocrHostService;

    @Autowired
    public OCRHostController(OCRHostService ocrHostService) {
        this.ocrHostService = ocrHostService;
    }


    @PostMapping("/host")
    public ResponseEntity<String> processFileWithTemporaryStorage(@RequestParam("file") MultipartFile file) {
        File tempFile = null;

        try {
            // 임시 디렉토리에 파일 저장
            tempFile = File.createTempFile("ocr_", ".jpg");
            file.transferTo(tempFile);

            // OCR 처리
            OcrResultDto resultDto = ocrHostService.processImage(tempFile.getAbsolutePath());

            return ResponseEntity.ok("OCR 처리 완료: " + resultDto);

        } catch (IOException e) {
            return ResponseEntity.status(500).body("파일 처리 실패: " + e.getMessage());
        }
    }

}
