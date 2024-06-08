FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} repair-service.jar
ENTRYPOINT ["java", "-jar", "/repair-service.jar"]