# Stage 1: Build the application using Maven
FROM maven:3.9.4-eclipse-temurin-21-alpine AS build
WORKDIR /app

# Copy the project and .env file
COPY . .
COPY .env .env

RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM openjdk:21-jdk-slim
WORKDIR /app

# Copy the JAR and .env
COPY --from=build /app/target/*.jar book-review.jar
COPY --from=build /app/.env .env

# Ensure the dotenv file can be read
ENV DOTENV_PATH="/app/.env"

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "book-review.jar"]
