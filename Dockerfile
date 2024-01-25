# Use an official OpenJDK runtime as a parent image
FROM maven:3.8.4-openjdk-11

# Set the working directory to /app
WORKDIR /app

# Copy the project files
COPY pom.xml .

# Copy the entire project and build it
COPY . .

# Run Maven to clean install your application
RUN mvn clean install

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "target/example-api-1.0.0.jar"]
