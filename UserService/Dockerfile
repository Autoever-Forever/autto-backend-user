# 1. Gradle 빌드를 포함한 멀티스테이지 빌드
FROM gradle:7.6-jdk17 AS build

# 작업 디렉토리 설정
WORKDIR /app

# Gradle wrapper 포함된 파일과 의존성 복사 및 Gradle 캐싱 활용
COPY gradle/wrapper/ /app/gradle/wrapper/
COPY build.gradle settings.gradle /app/
RUN gradle dependencies

# 나머지 소스코드 복사 및 빌드
COPY . /app
RUN ./gradlew build -x test

# 2. 최종 런타임 이미지 생성
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

# Spring Boot 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]

#