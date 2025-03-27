package com.bantvegas.aidocspro.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class PdfExportService {

    public byte[] generatePdfFromText(String ocrText) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        try (PDPageContentStream content = new PDPageContentStream(document, page)) {
            content.beginText();
            content.setFont(PDType1Font.HELVETICA, 12);
            content.setLeading(14.5f);
            content.newLineAtOffset(50, 750);

            String[] lines = ocrText.split("\\r?\\n");
            for (String line : lines) {
                content.showText(line);
                content.newLine();
            }

            content.endText();
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        document.save(out);
        document.close();

        return out.toByteArray();
    }
}
