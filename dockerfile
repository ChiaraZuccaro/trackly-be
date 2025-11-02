FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
# Copia il jar generato da Maven
ARG JAR_FILE=target/trackly-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
