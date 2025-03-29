FROM eclipse-temurin:21-jdk

# Inštalácia Tesseract-u cez apt
RUN apt-get update && \
    apt-get install -y tesseract-ocr && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Pracovný adresár
WORKDIR /app

# Skopíruj JAR
COPY target/aidocspro-ocr-backend-1.0.0.jar app.jar

# Skopíruj tessdata
COPY src/main/resources/tessdata /app/tessdata

# Nastav premenne pre Tess4J
ENV TESSDATA_PREFIX=/app/tessdata

# Štart
ENTRYPOINT ["java", "-jar", "app.jar"]
