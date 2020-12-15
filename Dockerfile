# For Java 8, try this
FROM openjdk:8-jdk-alpine

# For Java 11, try this
# FROM adoptopenjdk/openjdk11:alpine-jre

ARG JAR_FILE=build/libs/*.jar

# cd /opt/app
WORKDIR /opt/app

# cp build/libs/*.jar to  /opt/app/app.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","app.jar"]