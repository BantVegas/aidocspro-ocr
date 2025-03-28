FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY . .

RUN ./mvnw clean install -DskipTests

CMD ["java", "-jar", "target/aidocspro-ocr-backend-1.0.0.jar"]
