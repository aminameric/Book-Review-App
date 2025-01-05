FROM maven:3.9.4-eclipse-temurin-21-alpine AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim
COPY --from=build /target/book-review-app-0.0.1-SNAPSHOT.jar book-review.jar
EXPOSE 8080

ENTRYPOINT ["java","-jar","book-review.jar"]