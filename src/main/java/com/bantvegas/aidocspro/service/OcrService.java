package com.bantvegas.aidocspro.service;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class OcrService {

    private final TesseractDataPathProvider dataPathProvider;

    public OcrService(TesseractDataPathProvider dataPathProvider) {
        this.dataPathProvider = dataPathProvider;
    }

    public String performOcr(MultipartFile file, String language) throws IOException, TesseractException {
        System.out.println(">>> Spúšťam OCR");
        System.out.println(">>> Jazyk: " + language);
        System.out.println(">>> Názov súboru: " + file.getOriginalFilename());

        File tempFile = File.createTempFile("ocr_", "_" + file.getOriginalFilename());
        file.transferTo(tempFile);

        Tesseract tesseract = new Tesseract();
        tesseract.setLanguage(language);
        tesseract.setDatapath(dataPathProvider.getTessdataPath());

        try {
            String result = tesseract.doOCR(tempFile);
            System.out.println(">>> OCR úspešné");
            return result;
        } catch (TesseractException e) {
            System.err.println("❌ Chyba OCR: " + e.getMessage());
            throw e;
        }
    }
}
