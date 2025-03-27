package com.bantvegas.aidocspro.service;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.*;

@Service
public class OcrService {
    private final Tesseract tesseract;

    public OcrService() {
        this.tesseract = new Tesseract();
        try {
            Path tessdataDir = prepareTessdata();
            tesseract.setDatapath(tessdataDir.toAbsolutePath().toString());
        } catch (IOException e) {
            throw new RuntimeException("Nepodarilo sa pripraviť tessdata", e);
        }
    }

    public String doOcr(File file, String lang) throws TesseractException {
        tesseract.setLanguage(lang);
        return tesseract.doOCR(file);
    }

    public String performOcr(MultipartFile multipartFile, String lang) throws IOException, TesseractException {
        File tempFile = File.createTempFile("ocr_", "_" + multipartFile.getOriginalFilename());
        multipartFile.transferTo(tempFile);
        String result = doOcr(tempFile, lang);
        tempFile.delete();
        return result;
    }

    private Path prepareTessdata() throws IOException {
        Path tempDir = Files.createTempDirectory("tessdata");
        String[] langs = {"slk", "eng", "deu", "fra", "spa"};
        for (String lang : langs) {
            copyResource("tessdata/" + lang + ".traineddata", tempDir);
        }
        return tempDir;
    }

    private void copyResource(String resourcePath, Path targetDir) throws IOException {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            if (input == null) {
                throw new FileNotFoundException("Resource not found: " + resourcePath);
            }
            Path outputPath = targetDir.resolve(Paths.get(resourcePath).getFileName().toString());
            Files.copy(input, outputPath, StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
