# Use a lightweight Java image as the base
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the application JAR file to the container
COPY target/FishingGame-0.0.1-SNAPSHOT.jar app.jar

# Copy the .env file to the container
COPY .env /app/.env

# Expose the application port
EXPOSE 8080

# Set the entrypoint command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
