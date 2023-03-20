# Release Tracker

This is a simple release tracker application that allows you to list, create, update and delete releases. The application is built with Spring Boot and uses a PostgreSQL database for data storage.

## Tech

Release Tracker uses a number of open source projects to work properly:

- Spring framework - a powerful and flexible framework for building Java applications.
- Hibernate - an object-relational mapping tool that provides a framework for mapping an object-oriented domain model to a traditional relational database.
- PostgreSQL - a powerful and open source object-relational database system.
- Flyway - a database migration tool that makes it easy to manage database schema changes.
- Springdoc-openapi - a tool for automatically generating API documentation from Spring MVC endpoints.
- JUnit - a popular testing framework for Java applications.
- Testcontainers - a Java library that provides lightweight, throwaway instances of common databases, Selenium web browsers, or anything else that can run in a Docker container.

## Installation

Release tracker requires docker and docker-compose to run.
1. Clone the repository
    ```sh
    git clone https://github.com/Kabowyad/releaseTracker
    ```

2. Change directory to the project folder:
    ```sh
    cd release-tracker
    ```
3. Build the Docker images:
    ```sh
    docker-compose build -f <compose-file-name>
    ```
4. Start the application:
    ```sh
    docker-compose up -f <compose-file-name>
    ```

5. Access the application at http://localhost:8080.
6. The Swagger UI page will then be available at http://localhost:8080/swagger-ui/index.html
   The OpenAPI description will be available at the following url for json format  http://localhost:8080/v3/api-docs

The application folder has two Docker Compose files.
1. The docker-compose.light.yml file is for starting the application and database only.
2. The docker-compose.yml file is for starting the application, database, Grafana.
## License
