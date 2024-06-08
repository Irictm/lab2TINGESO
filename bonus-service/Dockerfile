FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} bonus-service.jar
ENTRYPOINT ["java", "-jar", "/bonus-service.jar"]