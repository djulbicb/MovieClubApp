FROM maven:3.8.5-openjdk-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src src
RUN mvn package -DskipTests
FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=builder /app/target/movieClub-0.0.1-SNAPSHOT.jar movieClub-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD ["java", "-jar", "movieClub-0.0.1-SNAPSHOT.jar"]

