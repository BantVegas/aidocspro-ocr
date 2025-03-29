package com.bantvegas.aidocspro.service;

import com.bantvegas.aidocspro.util.ImageConverter;
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
        // Ulož súbor do dočasného súboru
        File tempFile = File.createTempFile("ocr-upload-", ".tmp");
        file.transferTo(tempFile);

        try {
            // Konverzia do bežného formátu
            File safeImage = ImageConverter.convertToStandardImageFormat(tempFile);

            Tesseract tesseract = new Tesseract();
            tesseract.setDatapath(tessdataPathProvider.getTessdataPath());
            tesseract.setLanguage(language);

            System.out.println("🧠 Spúšťam OCR nad súborom: " + safeImage.getAbsolutePath());
            return tesseract.doOCR(safeImage);

        } catch (Exception e) {
            System.err.println("❌ Výnimka počas OCR: " + e.getClass().getSimpleName() + " – " + e.getMessage());
            throw e;
        } finally {
            tempFile.delete(); // vymaž pôvodný upload
        }
    }
}
