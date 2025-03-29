package com.bantvegas.aidocspro.service;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class OcrService {

    @Autowired
    private TesseractDataPathProvider tessdataPathProvider;

    public String performOcr(MultipartFile file, String language) throws IOException, TesseractException {
        File tempFile = File.createTempFile("ocr-upload-", ".tmp");
        file.transferTo(tempFile);

        try {
            Tesseract tesseract = new Tesseract();
            tesseract.setDatapath(tessdataPathProvider.getTessdataPath());
            tesseract.setLanguage(language);

            System.out.println("🧠 Spúšťam OCR nad súborom: " + tempFile.getAbsolutePath());
            return tesseract.doOCR(tempFile);

        } catch (Exception e) {
            System.err.println("❌ Chyba počas OCR: " + e.getClass().getSimpleName() + " – " + e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            tempFile.delete(); // uprace dočasný súbor
        }
    }
}
