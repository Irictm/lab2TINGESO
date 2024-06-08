FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} repair-list-service.jar
ENTRYPOINT ["java", "-jar", "/repair-list-service.jar"]