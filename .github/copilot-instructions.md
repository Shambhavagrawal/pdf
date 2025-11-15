# AI Copilot Instructions for PDF Processing Project

## Project Overview
This is a **Spring Boot 3.5.7** application for PDF processing using **Spring AI**. The project integrates Spring AI's PDF Document Reader to enable intelligent document processing with AI capabilities.

## Key Architecture & Dependencies
- **Framework**: Spring Boot 3.5.7 with Spring Web starter
- **Core Feature**: `spring-ai-pdf-document-reader` for PDF parsing
- **Java Version**: Java 25
- **Build Tool**: Maven with Spring Boot Maven Plugin
- **Testing**: JUnit 5 via Spring Boot Test starter
- **API Documentation**: `springdoc-openapi-starter-webmvc-ui` v2.8.14 for OpenAPI 3.1 and Swagger UI with BOM dependency management
- **Javadoc Introspection**: `therapi-runtime-javadoc` for automatic Javadoc comment introspection in OpenAPI specs
- **Validation**: `spring-boot-starter-validation` for JSR-303 request/response validation

## Project Structure
```
src/main/java/com/example/pdf/
  ├── PdfApplication.java                      # Entry point with @SpringBootApplication
  ├── config/
  │   └── OpenApiConfig.java                   # OpenAPI 3.1 configuration with API metadata
  ├── controller/
  │   ├── GreetingController.java              # Greeting endpoint with full OpenAPI documentation
  │   └── PdfProcessingController.java         # PDF processing endpoints
  ├── dto/
  │   ├── HealthCheckResponse.java             # Response DTO for health check endpoint
  │   └── ErrorResponse.java                   # Generic error response DTO
  └── exception/
      └── GlobalExceptionHandler.java          # Centralized exception handling with @ControllerAdvice
src/main/resources/
  └── application.properties                   # Spring Boot configuration with enhanced springdoc settings
src/test/java/com/example/pdf/
  └── PdfApplicationTests.java                 # Integration test template
```

## Build & Run Commands
- **Build**: `mvn clean package` (Windows: `mvnw.cmd clean package`)
- **Run**: `mvn spring-boot:run` (Windows: `mvnw.cmd spring-boot:run`)
- **Test**: `mvn test` (Windows: `mvnw.cmd test`)

## Development Conventions
1. **Package Structure**: All application code follows `com.example.pdf` namespace
   - Controllers: `com.example.pdf.controller`
   - DTOs: `com.example.pdf.dto`
   - Exception Handlers: `com.example.pdf.exception`
   - Configuration: `com.example.pdf.config`
2. **Spring Boot Configuration**: Comprehensive springdoc configuration in `application.properties` with optimized OpenAPI scanning and Swagger UI settings
3. **Testing Pattern**: Use `@SpringBootTest` for integration tests to load full Spring context
4. **REST Controllers**: All controllers must include full OpenAPI annotations (`@RestController`, `@RequestMapping`, `@Tag`)
5. **Response DTOs**: All endpoint responses must use typed DTOs with `@Schema` annotations instead of raw `Map` or generic types
6. **Error Handling**: Use centralized `GlobalExceptionHandler` with `@ControllerAdvice` for consistent error responses
7. **Javadoc Support**: Javadoc comments on methods are introspected by springdoc and included in OpenAPI specs
8. **OpenAPI Documentation**: Swagger UI available at `http://localhost:8081/swagger-ui.html`; OpenAPI 3.1 JSON at `http://localhost:8081/v3/api-docs`

## API Documentation Standards (OpenAPI/Swagger)

### Mandatory Annotations for Every REST Endpoint
Every REST controller and method must include the following annotations for automatic OpenAPI documentation:

1. **Controller-Level Annotations**:
   ```java
   @RestController
   @RequestMapping("/api/v1/your-resource")
   @Tag(name = "Your Resource", description = "Description of what this controller does")
   public class YourResourceController { }
   ```

2. **Method-Level Annotations**:
   ```java
   /**
    * Javadoc describing what this endpoint does.
    * This description will appear in the OpenAPI specification.
    * 
    * @param id the resource identifier
    * @return the resource response
    */
   @GetMapping("/{id}")
   @Operation(summary = "Short operation summary", 
              description = "Longer description of what this endpoint does and when to use it")
   @ApiResponses(value = {
       @ApiResponse(responseCode = "200", description = "Successful operation"),
       @ApiResponse(responseCode = "404", description = "Resource not found"),
       @ApiResponse(responseCode = "500", description = "Internal server error")
   })
   public ResponseEntity<YourDTO> getResource(
       @PathVariable @Parameter(description = "Resource identifier") String id) {
       // implementation
   }
   ```

3. **Request/Response DTO Annotations**:
   ```java
   @Schema(description = "Data Transfer Object for Your Resource")
   public class YourDTO {
       @Schema(description = "Unique identifier", example = "123")
       private String id;
       
       @Schema(description = "Resource name", example = "Example Name")
       @NotBlank(message = "Name cannot be blank")
       private String name;
   }
   ```

4. **Error Response Handling**:
   ```java
   // Use the centralized GlobalExceptionHandler
   // Throw appropriate exceptions and they will be automatically documented
   // Error responses are automatically documented with @ResponseStatus annotations
   @ExceptionHandler(ResourceNotFoundException.class)
   @ResponseStatus(HttpStatus.NOT_FOUND)
   public ResponseEntity<ErrorResponse> handleNotFound(Exception ex, WebRequest request) {
       // Implementation automatically creates proper error response
   }
   ```

### Key Annotation Reference

| Annotation | Location | Purpose |
|---|---|---|
| `@RestController` | Class | Marks class as REST endpoint handler |
| `@RequestMapping` | Class/Method | Defines base path for endpoints |
| `@Tag` | Class | Groups endpoints in Swagger UI |
| `@Operation` | Method | Describes what the endpoint does |
| `@ApiResponse` | Method | Documents possible HTTP responses (200, 404, 500, etc.) |
| `@ApiResponses` | Method | Container for multiple @ApiResponse annotations |
| `@Parameter` | Parameter | Documents request parameters (path, query, header) |
| `@RequestBody` | Parameter | Documents request body with schema |
| `@Schema` | Class/Field | Defines data model structure and validation constraints |
| `@GetMapping`, `@PostMapping`, etc. | Method | HTTP method mapping |

### Response Code Standards
Use standard HTTP status codes consistently:
- `200`: Successful GET/PUT request
- `201`: Successful POST request (resource created)
- `204`: Successful DELETE request (no content)
- `400`: Bad request (validation error)
- `401`: Unauthorized (authentication required)
- `403`: Forbidden (insufficient permissions)
- `404`: Resource not found
- `500`: Internal server error
- `503`: Service unavailable

### Documentation Examples

**Example 1: File Upload Endpoint**
```java
@PostMapping("/upload")
@Operation(summary = "Upload PDF document", 
           description = "Upload a PDF document for processing with AI capabilities")
@ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "PDF uploaded successfully"),
    @ApiResponse(responseCode = "400", description = "Invalid file format or size exceeds limit"),
    @ApiResponse(responseCode = "500", description = "Processing error")
})
public ResponseEntity<PdfUploadResponse> uploadPdf(
    @RequestPart("file") @Parameter(description = "PDF file to upload") MultipartFile file) {
    // implementation
}
```

**Example 2: Query with Validation**
```java
@GetMapping("/search")
@Operation(summary = "Search PDF documents", description = "Search documents by query parameters")
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Search results returned"),
    @ApiResponse(responseCode = "400", description = "Invalid search parameters")
})
public ResponseEntity<List<PdfDocumentDTO>> search(
    @RequestParam @Parameter(description = "Search query string") String query,
    @RequestParam(defaultValue = "0") @Parameter(description = "Page number (0-indexed)") int page,
    @RequestParam(defaultValue = "10") @Parameter(description = "Results per page") int size) {
    // implementation
}
```

### Important Notes
- **Javadoc is mandatory**: Every controller method must have Javadoc describing its purpose. These comments are automatically introspected and included in OpenAPI specifications via `therapi-runtime-javadoc`
- **Use @Parameter for all parameters**: Even simple parameters should be documented with descriptions
- **Use @Schema on DTOs**: All request/response DTOs must have `@Schema` annotations with descriptions and examples
- **Use typed DTOs instead of Map**: All endpoint responses must return properly typed DTOs with `@Schema` annotations, never raw `Map<String, String>` or generic types
- **Validation annotations are documented**: JSR-303 annotations like `@NotNull`, `@Min`, `@Max`, `@Size` are automatically included in OpenAPI spec
- **Content schema in responses**: Always include `content = @Content(schema = @Schema(implementation = YourDTO.class))` in `@ApiResponse` annotations
- **Keep descriptions concise**: Summaries should be 1-2 sentences; use description for longer explanations
- **Error responses**: All errors are handled by `GlobalExceptionHandler` and return `ErrorResponse` DTO with proper HTTP status codes

### Available Tags
When creating new controllers, use existing tags or define new ones in `OpenApiConfig.java`:
- **Greeting**: For greeting and test endpoints
- **PDF Processing**: For PDF upload, processing, and document operations

Whenever adding new functionality, update the tags in `OpenApiConfig.java` if a new category is needed.

## Integration Points
- **Spring AI PDF Reader**: Documentation at https://docs.spring.io/spring-ai/reference/api/etl-pipeline.html#_pdf_page
- **RESTful Endpoints**: Reference Spring's REST guides for implementing PDF upload/processing endpoints
- **OpenAPI/Swagger**: Documentation at https://springdoc.org for integrating OpenAPI specs and Swagger UI
- **Javadoc Introspection**: Javadoc comments are automatically processed via `therapi-runtime-javadoc`. Ensure all controller methods have proper Javadoc for full OpenAPI documentation

## Springdoc Configuration Details

### Application Properties (application.properties)
```properties
# Core OpenAPI Configuration
springdoc.api-docs.path=/v3/api-docs                                    # JSON OpenAPI spec endpoint
springdoc.swagger-ui.path=/swagger-ui.html                              # Swagger UI endpoint
springdoc.api-docs.version=openapi_3_1                                  # OpenAPI 3.1 specification

# Package and Path Scanning (Performance Optimization)
springdoc.packages-to-scan=com.example.pdf                              # Only scan our packages
springdoc.paths-to-match=/api/**                                        # Only match /api paths

# Swagger UI Customization
springdoc.swagger-ui.try-it-out-enabled=true                            # Enable "Try it out" button
springdoc.swagger-ui.operations-sorter=method                           # Sort endpoints by HTTP method
springdoc.swagger-ui.tags-sorter=alpha                                  # Alphabetically sort tags
springdoc.swagger-ui.doc-expansion=list                                 # Expand only tags by default
springdoc.swagger-ui.persist-authorization=true                         # Remember authentication

# Default Media Types
springdoc.default-consumes-media-type=application/json                  # Default request format
springdoc.default-produces-media-type=application/json                  # Default response format

# Javadoc and Schema Support
springdoc.api-docs.resolve-schema-properties=true                       # Resolve schema properties from Javadoc
```

### Javadoc Introspection Setup
To enable Javadoc comment introspection in OpenAPI specs:

1. **Dependencies already added**: `therapi-runtime-javadoc` and `therapi-runtime-javadoc-scribe` in `pom.xml`
2. **How it works**: Javadoc comments on controller methods are automatically extracted at compile time and included in OpenAPI specs
3. **Example**:
   ```java
   /**
    * This Javadoc comment will appear as the operation description in OpenAPI
    * 
    * @param id the resource identifier
    * @return the resource response
    */
   @GetMapping("/{id}")
   public ResponseEntity<YourDTO> getResource(@PathVariable String id) { ... }
   ```

## When Adding Features
- Add new controllers to `src/main/java/com/example/pdf/controller/`
- Add new DTOs to `src/main/java/com/example/pdf/dto/`
- Add exception handlers to `src/main/java/com/example/pdf/exception/` or update `GlobalExceptionHandler`
- Add test cases to `src/test/java/com/example/pdf/` maintaining the `@SpringBootTest` pattern
- **Always include full OpenAPI documentation** with `@RestController`, `@Tag`, `@Operation`, `@ApiResponse`, `@Parameter`, and `@Schema` annotations (see API Documentation Standards section above)
- Use Maven's Spring Boot plugin for building Docker images if needed
- Register new API tags in `OpenApiConfig.java` if adding a new feature category
- Create corresponding response DTOs with `@Schema` annotations instead of using raw `Map` types
