FROM eclipse-temurin:21-jdk

# Inštaluj tesseract
RUN apt-get update && \
    apt-get install -y tesseract-ocr && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Skopíruj JAR súbor (ten, čo si buildol cez `mvn clean package`)
COPY target/aidocspro-ocr-backend-1.0.0.jar app.jar

# Skopíruj tessdata
COPY src/main/resources/tessdata /app/tessdata

ENV TESSDATA_PREFIX=/app/tessdata

ENTRYPOINT ["java", "-jar", "app.jar"]
