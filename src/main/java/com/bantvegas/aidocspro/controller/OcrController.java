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
@CrossOrigin(origins = "*") // Povolenie pre frontend (napr. z Vercelu)
public class OcrController {

    @Autowired
    private OcrService ocrService;

    /**
     * POST /api/ocr – Hlavný endpoint pre vykonanie OCR
     */
    @PostMapping
    public ResponseEntity<?> handleOcr(
            @RequestParam("file") MultipartFile file,
            @RequestParam("language") String language) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("❌ Prázdny súbor.");
        }

        try {
            System.out.println("➡️ OCR požiadavka prijatá: " + file.getOriginalFilename() + " / " + language);
            String result = ocrService.performOcr(file, language);
            return ResponseEntity.ok(result);

        } catch (Exception e) {
            e.printStackTrace(); // Zobrazí výnimku v Railway logoch
            // Zobrazí výnimku aj priamo vo fronte
            return ResponseEntity.internalServerError()
                    .body("❌ Výnimka: " + e.getClass().getSimpleName() + " – " + e.getMessage());
        }
    }

    /**
     * GET /api/ocr/test – Testovací endpoint pre overenie funkčnosti
     */
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("✅ OCR backend beží správne");
    }
}
