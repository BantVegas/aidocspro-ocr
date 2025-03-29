
FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY target/aidocspro-ocr-backend-1.0.0.jar app.jar
COPY src/main/resources/tessdata ./tessdata

ENV TESSDATA_PREFIX=/app/tessdata

ENTRYPOINT ["java", "-jar", "app.jar"]
