# Use the official OpenJDK image as the base image
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the jar file from the target directory into the container
COPY target/*.jar karate.jar

# Expose the application port (adjust if necessary)
EXPOSE 8085

# Command to run the application
ENTRYPOINT ["java", "-jar", "karate.jar"]
