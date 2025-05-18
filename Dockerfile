# 1. JDK가 설치된 경량 리눅스 이미지 (알파인 기반)
FROM eclipse-temurin:17-jdk

# 2. 컨테이너 안에서 jar 파일을 실행할 작업 폴더 설정
WORKDIR /app

# 3. 빌드된 JAR 파일을 컨테이너 안으로 복사
COPY build/libs/PingGu-Backend-0.0.1-SNAPSHOT.jar app.jar

# 4. Spring Boot 기본 포트 (fly.toml에서 이 포트를 열어야 함)
EXPOSE 8080

# 5. 컨테이너 시작 시 실행할 명령어
ENTRYPOINT ["java", "-jar", "app.jar"]
