# Stage 1 – Build JAR
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /build
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2 – Run JAR + Tesseract
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Inštalácia Tesseract OCR
RUN apt-get update -qq && \
    apt-get install -qq -y tesseract-ocr && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

COPY --from=build /build/target/*.jar app.jar
COPY src/main/resources/tessdata /app/tessdata
ENV TESSDATA_PREFIX=/app/tessdata

ENTRYPOINT ["java", "-jar", "app.jar"]
