FROM openjdk:11-jdk
LABEL name="shopping-category"

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "/app.jar"]
