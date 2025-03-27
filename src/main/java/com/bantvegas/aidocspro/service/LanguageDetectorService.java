package com.bantvegas.aidocspro.service;

import com.github.pemistahl.lingua.api.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LanguageDetectorService {

    private final LanguageDetector detector;

    public LanguageDetectorService() {
        this.detector = LanguageDetectorBuilder.fromAllLanguages().build();
    }

    public Optional<String> detectLanguage(String text) {
        if (text == null || text.trim().isEmpty()) return Optional.empty();
        Language detected = detector.detectLanguageOf(text);
        return Optional.ofNullable(detected.getIsoCode639_3().toString());
    }
}
