# AI Copilot Instructions for PDF Processing Project

## Project Overview
This is a **Spring Boot 3.5.7** application for PDF processing using **Spring AI**. The project integrates Spring AI's PDF Document Reader to enable intelligent document processing with AI capabilities.

## Key Architecture & Dependencies
- **Framework**: Spring Boot 3.5.7 with Spring Web starter
- **Core Feature**: `spring-ai-pdf-document-reader` for PDF parsing
- **Java Version**: Java 25
- **Build Tool**: Maven with Spring Boot Maven Plugin
- **Testing**: JUnit 5 via Spring Boot Test starter

## Project Structure
```
src/main/java/com/example/pdf/
  └── PdfApplication.java        # Entry point with @SpringBootApplication
src/main/resources/
  └── application.properties      # Spring Boot configuration
src/test/java/com/example/pdf/
  └── PdfApplicationTests.java    # Integration test template
```

## Build & Run Commands
- **Build**: `mvn clean package` (Windows: `mvnw.cmd clean package`)
- **Run**: `mvn spring-boot:run` (Windows: `mvnw.cmd spring-boot:run`)
- **Test**: `mvn test` (Windows: `mvnw.cmd test`)

## Development Conventions
1. **Package Structure**: All application code follows `com.example.pdf` namespace
2. **Spring Boot Configuration**: Minimal setup in `application.properties` - extend as needed for PDF reader settings
3. **Testing Pattern**: Use `@SpringBootTest` for integration tests to load full Spring context
4. **No Controllers Yet**: Project is bare bones; add REST controllers under `com.example.pdf` to expose PDF processing endpoints

## Integration Points
- **Spring AI PDF Reader**: Documentation at https://docs.spring.io/spring-ai/reference/api/etl-pipeline.html#_pdf_page
- **RESTful Endpoints**: Reference Spring's REST guides for implementing PDF upload/processing endpoints

## When Adding Features
- Add new controllers to `src/main/java/com/example/pdf/controller/`
- Add test cases to `src/test/java/com/example/pdf/` maintaining the `@SpringBootTest` pattern
- Use Maven's Spring Boot plugin for building Docker images if needed
