# Build stage
FROM openjdk:17-jdk-alpine AS build
WORKDIR /app
COPY . .
RUN ./gradlew build -x test

# Run stage
FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/build/libs/neontest-1.0-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "neontest-1.0-SNAPSHOT.jar"]
EXPOSE 8080
