package com.bantvegas.aidocspro.controller;

import com.bantvegas.aidocspro.service.TesseractDataPathProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
@RequestMapping("/api/ocr")
@CrossOrigin(origins = "*")
public class OcrDebugController {

    @Autowired
    private TesseractDataPathProvider dataPathProvider;

    @GetMapping("/debug")
    public ResponseEntity<String> debug() {
        String tessdataPath = dataPathProvider.getTessdataPath();

        StringBuilder sb = new StringBuilder();
        sb.append("✅ OCR Debug Info\n");
        sb.append("Tessdata path: ").append(tessdataPath).append("\n");

        File tessdataDir = new File(tessdataPath);
        if (tessdataDir.exists() && tessdataDir.isDirectory()) {
            sb.append("Obsah tessdata:\n");
            File[] files = tessdataDir.listFiles();
            if (files != null && files.length > 0) {
                for (File f : files) {
                    sb.append(" - ").append(f.getName()).append("\n");
                }
            } else {
                sb.append("⚠️ Tessdata priečinok je prázdny\n");
            }
        } else {
            sb.append("❌ Tessdata priečinok neexistuje!\n");
        }

        return ResponseEntity.ok(sb.toString());
    }
}
