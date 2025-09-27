# 1. Stage: Build app with Gradle
FROM gradle:8.6.0-jdk21 AS build

WORKDIR /app

# 1️⃣ Gradle build fayllarını kopyalayırıq
COPY build.gradle settings.gradle gradle.properties ./

# 2️⃣ Dependencies-ləri yükləyirik (cache istifadə olunur)
RUN gradle dependencies --no-daemon

# 3️⃣ Source kodunu kopyalayırıq
COPY src ./src

# 4️⃣ App-i build edirik (testsiz)
RUN gradle build -x test --no-daemon

# 2. Stage: Run app
FROM openjdk:21-jdk-slim

WORKDIR /app

# 5️⃣ Build olunmuş jar-ı birinci stage-dən kopyalayırıq
COPY --from=build /app/build/libs/simple-banking-system-0.0.1-SNAPSHOT.jar ./simple-banking-system-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "simple-banking-system-0.0.1-SNAPSHOT.jar"]
