
FROM gradle:8.7-jdk17 AS builder

WORKDIR /app

COPY build.gradle.kts .
COPY settings.gradle .

COPY src ./src

RUN gradle build --no-daemon -DskipTests

FROM openjdk:17-jdk-slim

WORKDIR /app


COPY --from=builder /app/build/libs/killhimka-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8085


ENTRYPOINT ["java", "-jar", "app.jar"]