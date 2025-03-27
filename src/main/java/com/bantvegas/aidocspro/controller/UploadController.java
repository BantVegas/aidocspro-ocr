package com.bantvegas.aidocspro.controller;

import com.bantvegas.aidocspro.dto.OcrResponse;
import com.bantvegas.aidocspro.service.LanguageDetectorService;
import com.bantvegas.aidocspro.service.OcrService;
import com.bantvegas.aidocspro.service.PdfExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/ocr")
@CrossOrigin(origins = "*")
public class UploadController {

    @Autowired
    private OcrService ocrService;

    @Autowired
    private PdfExportService pdfExportService;

    @Autowired
    private LanguageDetectorService languageDetectorService;

    @PostMapping("/upload")
    public ResponseEntity<OcrResponse> uploadFile(@RequestParam("file") MultipartFile file,
                                                  @RequestParam("language") String language) {
        try {
            String extractedText = ocrService.performOcr(file, language);
            String detectedLanguage = languageDetectorService.detectLanguage(extractedText)
                    .orElse("unknown");

            OcrResponse response = new OcrResponse(
                    extractedText,
                    language,
                    detectedLanguage,
                    file.getOriginalFilename()
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new OcrResponse("OCR zlyhalo: " + e.getMessage(), language, "error", file.getOriginalFilename()));
        }
    }
}
