# --- Стадия сборки (Builder Stage) ---
# Используем официальный образ Gradle с JDK 17
# ОБНОВИВ Gradle ДО СОВМЕСТИМОЙ ВЕРСИИ (например, 8.7)
# <-- ИЗМЕНЕНО: Gradle 8.7
FROM gradle:8.7-jdk17 AS builder

WORKDIR /app

# Копируем файлы конфигурации Gradle
COPY build.gradle.kts .
# Проверь, есть ли у тебя settings.gradle или settings.gradle.kts
# <-- Убедись, что этот файл есть, или измени на settings.gradle.kts, или удали строку, если файла нет
COPY settings.gradle .
# COPY settings.gradle.kts . # <-- Если у тебя .kts версия

# Копируем исходный код
COPY src ./src

# Запускаем команду сборки Gradle.
# Убедись, что версии в build.gradle.kts совместимы с Gradle 8.7 и Java 17!
RUN gradle build --no-daemon -DskipTests

# --- Финальная стадия (Final Stage) ---
FROM openjdk:17-jdk-slim

WORKDIR /app

# Копируем JAR-файл. Убедись, что имя файла соответствует действительности.
# Если в build.gradle.kts настроено другое имя, измени его здесь.
COPY --from=builder /app/build/libs/killhimka-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8085

ENTRYPOINT["java", "-jar", "app.jar"]