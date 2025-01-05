# Stage 1: Build the application using Maven
FROM maven:3.9.4-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Create a minimal runtime image using OpenJDK
FROM openjdk:21-jdk-slim
WORKDIR /app

# Ensure the JAR path is correct and matches the build stage output
COPY --from=build /app/target/*.jar book-review.jar
COPY .env .env
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "book-review.jar"]
