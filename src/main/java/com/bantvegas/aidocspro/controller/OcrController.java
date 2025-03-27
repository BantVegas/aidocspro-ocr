package com.bantvegas.aidocspro.controller;

import com.bantvegas.aidocspro.service.OcrService;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@RestController
@RequestMapping("/api/ocr")
@CrossOrigin(origins = "*")
public class OcrController {

    @Autowired
    private OcrService ocrService;

    @PostMapping
    public ResponseEntity<?> handleOcr(
            @RequestParam("file") MultipartFile file,
            @RequestParam("language") String language) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Prázdny súbor.");
        }

        try {
            String result = ocrService.performOcr(file, language);
            return ResponseEntity.ok(result);

        } catch (TesseractException e) {
            return ResponseEntity.internalServerError().body("Chyba pri OCR: " + e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Chyba pri spracovaní súboru: " + e.getMessage());
        }
    }
}
