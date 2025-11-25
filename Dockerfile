FROM amazoncorretto:21-alpine
EXPOSE 8080
VOLUME /tmp
ARG JAR_FILE=*.jar
COPY inventory_system-0.0.5-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
