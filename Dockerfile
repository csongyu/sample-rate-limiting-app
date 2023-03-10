FROM openjdk:17.0.2
ARG JAR_FILE
ADD target/${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]