# 1. Build stage: use Maven + Java to compile your app
FROM maven:3.8.6-amazoncorretto-17 AS builder
WORKDIR /app

# Copy only the pom first to leverage layer caching
COPY pom.xml ./
RUN mvn dependency:go-offline -B

# Now copy source and build
COPY src ./src
RUN mvn clean package -DskipTests

# 2. Run stage: slim JRE image
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copy the fat‑jar from the builder stage
COPY --from=builder /app/target/RestaurantDineIn-0.0.1.jar app.jar

# Expose the port your app uses
EXPOSE 5034

# Launch the jar
ENTRYPOINT ["java","-jar","app.jar"]
