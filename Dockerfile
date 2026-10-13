FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /workspace
COPY pom.xml .
COPY src ./src
RUN mvn -B -DskipTests package

FROM eclipse-temurin:21-jre

WORKDIR /app
COPY --from=build /workspace/target/autohub-backend-1.0.0.jar app.jar
ENV SPRING_PROFILES_ACTIVE=render
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
