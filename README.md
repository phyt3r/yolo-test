
# YOLO Test Project

This project is a Spring Boot application designed to provide a basic framework for a web service. It includes essential configurations and setup to get started quickly.

## Project Structure

- `.gitignore` - Specifies files and directories to be ignored by Git.
- `.idea` - Directory containing IDE-specific settings (IntelliJ IDEA).
- `mvnw`, `mvnw.cmd` - Maven wrapper scripts for Unix and Windows.
- `pom.xml` - Maven Project Object Model file containing project dependencies and build configuration.
- `src` - Directory containing the source code.
- `target` - Directory where the built artifacts are stored.

## Prerequisites

- Java 11 or later
- Maven 3.6.3 or later

## Getting Started

### Build the Project

You can build the project using Maven. This will compile the source code, run tests, and package the application.

```bash
./mvnw clean install
```

### Run the Application

You can run the application using the Maven Spring Boot plugin. This will start the embedded Tomcat server on the default port (8080).

```bash
./mvnw spring-boot:run
```

### Access the Application

Once the application is running, you can access it at `http://localhost:8080/gamble`.

## Configuration

The application can be configured using the `application.properties` file located in the `src/main/resources` directory.

### Example Configuration (`application.properties`)

```properties
# Server Configuration
server.port=8080
```

## Custom Properties

You can define custom properties in the `application.properties` file and access them in your Spring components using the `@Value` annotation.

### Example

```java
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MyAppConfig {

    @Value("${myapp.custom.property1}")
    private String property1;

    @Value("${myapp.custom.property2}")
    private String property2;

    public void printProperties() {
        System.out.println("Property 1: " + property1);
        System.out.println("Property 2: " + property2);
    }
}
```

## Usage

### /gamble Endpoint

This application includes a `/gamble` endpoint which simulates a gambling operation. To use this endpoint, you can send a POST request to `/gamble`.

### Example Request

```bash
curl -X POST http://localhost:8080/gamble
```

```json
{
    "bet": 40.5,
    "guess": 50
}
```

### Example Response

```json
{
    "win": 80.19
}
```

## Acknowledgments

- Spring Boot documentation: [https://spring.io/projects/spring-boot](https://spring.io/projects/spring-boot)
- Maven: [https://maven.apache.org/](https://maven.apache.org/)
