FROM eclipse-temurin:17-jdk as builder

WORKDIR /app
COPY . .

RUN ./gradlew build --no-daemon

FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
