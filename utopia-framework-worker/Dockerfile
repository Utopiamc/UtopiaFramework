# AS <NAME> to name this stage as maven
FROM maven:3.6.3-openjdk-17 AS maven
LABEL MAINTAINER="utopiamc.de"

WORKDIR /usr/src/app
COPY . /usr/src/app
# Compile and package the application to an executable JAR
RUN mvn package

# For Java 11,
FROM openjdk:17.0.2-jdk

WORKDIR /opt/app

COPY --from=maven /usr/src/app/target/*.jar /opt/app/app.jar

ENTRYPOINT ["java","-jar","app.jar"]