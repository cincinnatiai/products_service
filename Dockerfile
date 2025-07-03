FROM openjdk:24-jdk-slim

WORKDIR /app

COPY target/inventory_system-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]