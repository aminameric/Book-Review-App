# Stage 1: Build
FROM maven:3.9.4-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Run
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/target/book-review-app-0.0.1-SNAPSHOT.jar book-review.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "book-review.jar"]
