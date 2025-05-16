# Java 21을 위한 베이스 이미지
FROM eclipse-temurin:21-jdk

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
