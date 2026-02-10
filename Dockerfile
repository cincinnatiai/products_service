FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY target/inventory_system-0.0.7-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]