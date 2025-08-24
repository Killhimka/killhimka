
FROM gradle:8.7-jdk17 AS builder
WORKDIR /app
COPY build.gradle.kts .
COPY settings.gradle .
COPY src ./src
# без тестов
# RUN gradle clean build --no-daemon -DskipTests
# RUN gradle build --no-daemon для запуска с тестами
COPY --from=builder /app/build/libs/killhimka-0.0.1-SNAPSHOT.jar app.jar

FROM gradle:jdk17-ubi-minimal
WORKDIR /app
COPY --from=builder /app/app.jar .
EXPOSE 8085
ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]