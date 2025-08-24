# --- Стадия сборки (Builder Stage) ---
# Используем официальный образ Gradle с JDK 17
# Это образ, который имеет Gradle и Java, чтобы собрать твой проект
FROM gradle:7.6.3-jdk17 AS builder

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем файлы конфигурации Gradle и исходный код
# Сначала копируем build.gradle.kts, чтобы Docker мог кешировать этот слой,
# если build.gradle.kts не менялся. Это ускоряет последующие сборки.
COPY build.gradle.kts .

# Теперь копируем остальные файлы build.gradle, settings.gradle и т.д.
# Если они есть в корне
COPY settings.gradle.kts .

# Копируем весь исходный код
COPY src ./src

# Запускаем команду сборки Gradle.
# `-DskipTests` пропустит запуск тестов.
# Если у тебя нет файла build.gradle.kts, а есть build.gradle, то используй:
# RUN gradle build --no-daemon -DskipTests
# Если у тебя build.gradle.kts, то:
RUN gradle build --no-daemon -DskipTests

# --- Финальная стадия (Final Stage) ---
# Используем чистый образ OpenJDK 17
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем ТОЛЬКО собранный JAR-файл из стадии 'builder'
# Имя JAR-файла зависит от того, как он настроен в build.gradle.kts
# Обычно, если используется плагин Spring Boot, он называется как-то вроде
# 'your-project-name-0.0.1-SNAPSHOT.jar'
# Или, если вы настроили bootJar { archiveBaseName = 'killhimka' }, то он будет killhimka.jar
# В вашем случае, из Build Command мы знаем, что это 'killhimka-0.0.1-SNAPSHOT.jar'
COPY --from=builder /app/build/libs/killhimka-0.0.1-SNAPSHOT.jar app.jar

# Открываем порт, на котором будет работать Spring Boot приложение (по умолчанию 8080)
EXPOSE 8085

# Указываем, какую команду выполнить при запуске контейнера
# 'app.jar' - это имя, под которым мы скопировали JAR-файл.
# Spring Boot автоматически подхватит порт из переменной окружения PORT,
# которую Render.com обычно устанавливает для Web Service.
ENTRYPOINT ["java", "-jar", "app.jar"]
