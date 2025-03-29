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
        File tempFile = File.createTempFile("ocr-upload-", ".tmp");
        file.transferTo(tempFile);

        try {
            File safeImage = ImageConverter.convertToStandardImageFormat(tempFile);

            Tesseract tesseract = new Tesseract();
            tesseract.setDatapath(tessdataPathProvider.getTessdataPath());
            tesseract.setLanguage(language);

            System.out.println("🧠 OCR nad: " + safeImage.getAbsolutePath());
            return tesseract.doOCR(safeImage);

        } catch (Exception e) {
            System.err.println("❌ CHYBA: " + e.getClass().getSimpleName() + " – " + e.getMessage());
            e.printStackTrace();
            // hodiť naspäť pre ResponseEntity
            throw new TesseractException("OCR zlyhalo: " + e.getMessage());
        } finally {
            tempFile.delete(); // vždy uprac
        }
    }
}
