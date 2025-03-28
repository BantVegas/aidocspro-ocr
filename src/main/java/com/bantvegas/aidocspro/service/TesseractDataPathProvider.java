package com.bantvegas.aidocspro.service;

import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Component
public class TesseractDataPathProvider {

    private String tessdataPath;

    @PostConstruct
    public void init() throws IOException {
        Path tempDir = Files.createTempDirectory("tessdata");
        File tessdataDir = new File(tempDir.toFile(), "tessdata");
        tessdataDir.mkdir();

        String[] trainedFiles = {
                "eng.traineddata", "slk.traineddata", "ces.traineddata",
                "deu.traineddata", "fra.traineddata", "spa.traineddata"
        };

        for (String fileName : trainedFiles) {
            try (InputStream is = getClass().getClassLoader().getResourceAsStream("tessdata/" + fileName)) {
                if (is != null) {
                    File outFile = new File(tessdataDir, fileName);
                    Files.copy(is, outFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("✔️ Skopírované: " + fileName);
                } else {
                    System.err.println("⚠️ Nenájdený súbor: " + fileName);
                }
            }
        }

        this.tessdataPath = tessdataDir.getAbsolutePath();
        System.out.println("✅ Tessdata pripravené v: " + tessdataPath);
    }

    public String getTessdataPath() {
        return tessdataPath;
    }
}
