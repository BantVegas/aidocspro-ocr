# Stage 1 – Build JAR
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /build
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2 – Run JAR
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Inštalácia Tesseract
RUN apt-get update -qq && \
    apt-get install -qq -y tesseract-ocr && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

# Skopírovanie vytvoreného JAR
COPY --from=build /build/target/aidocspro-ocr-backend-1.0.0.jar app.jar

# Tessdata do kontajnera
COPY src/main/resources/tessdata /app/tessdata

# Premenná pre Tesseract
ENV TESSDATA_PREFIX=/app/tessdata

# Spustenie aplikácie
ENTRYPOINT ["java", "-jar", "app.jar"]
