# Stage 1: build
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /build
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: run
FROM eclipse-temurin:21-jdk

# Inštalácia Tesseract
RUN apt-get update && \
    apt-get install -y tesseract-ocr && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY --from=build /build/target/aidocspro-ocr-backend-1.0.0.jar app.jar
COPY src/main/resources/tessdata /app/tessdata

ENV TESSDATA_PREFIX=/app/tessdata

ENTRYPOINT ["java", "-jar", "app.jar"]
