package com.bantvegas.aidocspro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OcrResponse {
    private String text;
    private String requestedLanguage;
    private String detectedLanguage;
    private String fileName;
}
