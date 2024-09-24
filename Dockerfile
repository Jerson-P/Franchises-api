# Fase de construcción
FROM eclipse-temurin:17-jdk-jammy as build
WORKDIR /app
COPY . /app
RUN ./gradlew clean build --no-daemon

# Fase de ejecución
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /app/build/libs/franchises-api.jar /app/franchises-api.jar
ENTRYPOINT ["java", "-jar", "/app/franchises-api.jar"]
