package com.bantvegas.aidocspro.controller;

import net.sourceforge.tess4j.Tesseract;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
@RequestMapping("/api/ocr")
@CrossOrigin(origins = "*")
public class OcrDebugController {

    @GetMapping("/debug")
    public ResponseEntity<String> debug() {
        Tesseract tesseract = new Tesseract();

        // Tessdata path – uprav podľa potreby
        String tessdataPath = "/app/tessdata";
        tesseract.setDatapath(tessdataPath);

        StringBuilder sb = new StringBuilder();
        sb.append("✅ OCR Debug Info\n");
        sb.append("Tessdata path: ").append(tessdataPath).append("\n");

        File tessdataDir = new File(tessdataPath);
        if (tessdataDir.exists() && tessdataDir.isDirectory()) {
            sb.append("Obsah tessdata:\n");
            File[] files = tessdataDir.listFiles();
            if (files != null) {
                for (File f : files) {
                    sb.append(" - ").append(f.getName()).append("\n");
                }
            } else {
                sb.append("⚠️ Žiadne súbory\n");
            }
        } else {
            sb.append("❌ Tessdata priečinok neexistuje!\n");
        }

        return ResponseEntity.ok(sb.toString());
    }
}
