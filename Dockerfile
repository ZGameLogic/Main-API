FROM ubuntu:latest
LABEL authors="Ben Shabowski"

FROM arm64v8/openjdk:21-jdk-buster

WORKDIR /app
COPY /target/API-1.0.0.jar /app/API-1.0.0.jar

EXPOSE 443

CMD ["java", "-jar", "-Dspring.profiles.active=cluster", "API-1.0.0.jar"]
