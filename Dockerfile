#FROM adoptopenjdk/openjdk11:jdk-11.0.2.9-slim
FROM --platform=linux/amd64 eclipse-temurin:21
WORKDIR /opt
COPY target/*.jar /opt/app.jar
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar