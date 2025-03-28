FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY . .

RUN apt-get update && apt-get install -y maven

RUN mvn clean install -DskipTests

CMD ["java", "-jar", "target/aidocspro-ocr-backend-1.0.0.jar"]
