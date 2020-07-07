FROM openjdk:8-jdk-alpine
ARG JAR_FILE=/api/target/*.jar
COPY ${JAR_FILE} api-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/api-0.0.1-SNAPSHOT.jar"]