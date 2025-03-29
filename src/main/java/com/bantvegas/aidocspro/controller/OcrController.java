// OcrController.java
package com.bantvegas.aidocspro.controller;

import com.bantvegas.aidocspro.service.OcrService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/ocr")
@CrossOrigin(origins = "*") // Povolenie pre frontend (napr. z Vercelu)
public class OcrController {

    @Autowired
    private OcrService ocrService;

    @PostMapping
    public ResponseEntity<?> handleOcr(
            @RequestParam("file") MultipartFile file,
            @RequestParam("language") String language) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("\u274C Pr\u00e1zdny s\u00fabor.");
        }

        try {
            System.out.println("\u27a1\ufe0f OCR po\u017eiadavka prijat\u00e1: " + file.getOriginalFilename() + " / " + language);
            String result = ocrService.performOcr(file, language);

            if (result.startsWith("\u274C V\u00fdnimka:")) {
                return ResponseEntity.internalServerError().body(result);
            }

            return ResponseEntity.ok(result);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body("\u274C Nezachyten\u00e1 v\u00fdnimka: " + e.getClass().getSimpleName() + " – " + e.getMessage());
        }
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("\u2705 OCR backend be\u017e\u00ed spr\u00e1vne");
    }
}