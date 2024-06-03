# Use the official image as a parent image
FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Add the project jar to the container
COPY build/libs/actors*.jar actorservice.jar

# Run the jar file
ENTRYPOINT ["java","-jar","actorservice.jar"]
