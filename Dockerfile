# Dockerfile

# --- Этап сборки ---
# Используем образ с Gradle и Java (укажите нужную версию)
# Пример: gradle:7.6.3-jdk17
# Если есть ошибка с '.' или '#', проверьте здесь путь к Gradle или номер версии.
FROM gradle:7.6.3-jdk17 AS builder

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем GRADLE WRAPPER файлы и конфигурацию.
# Убедитесь, что пути корректны и нет лишних символов.
# Например, если gradlew находится прямо в корне, а gradle/ wrapper/ - это поддиректории:
COPY gradlew .
COPY gradle/wrapper/gradle-wrapper.jar gradle/wrapper/
COPY gradle/wrapper/gradle-wrapper.properties gradle/wrapper/

# Копируем Gradle build файлы
COPY build.gradle.kts settings.gradle

# Копируем исходный код приложения
COPY src ./src

# Делаем gradlew исполняемым
RUN chmod +x gradlew

# Выполняем сборку проекта (создаем JAR)
RUN ./gradlew bootJar

# --- Продакшн-образ ---
# Используем минимальный образ с OpenJDK нужной версии
FROM openjdk:17-jdk-slim AS runner

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем собранный JAR-файл из образа сборки
# Убедитесь, что имя файла ТОЧНОЕ: killhimka-0.0.1-SNAPSHOT.jar
COPY --from=builder /app/build/libs/killhimka-0.0.1-SNAPSHOT.jar ./app.jar

# Указываем команду запуска
# CMD ["java", "-jar", "/app/app.jar"] # Можно указать полный путь
CMD ["java", "-jar", "app.jar"]
