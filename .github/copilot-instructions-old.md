# PDF Processing Project - Copilot Instructions

## Project Overview
**Spring Boot 3.5.7** application for PDF processing using **Spring AI** with PDF Document Reader integration. Provides RESTful APIs with comprehensive OpenAPI 3.1 documentation for intelligent document processing.

## Technology Stack
- **Framework**: Spring Boot 3.5.7 (Java 25)
- **Core Features**: Spring AI PDF Reader (v1.1.0), Spring Web
- **API Documentation**: springdoc-openapi v2.8.14 with Javadoc introspection
- **Validation**: JSR-303 Bean Validation
- **Build**: Maven with Spring Boot Plugin
- **Testing**: JUnit 5 (Spring Boot Test)

## Project Structure
```
src/main/java/com/example/pdf/
  ├── PdfApplication.java                 # Main entry point
  ├── config/OpenApiConfig.java           # OpenAPI 3.1 metadata
  ├── controller/                         # REST endpoints (see controller-guidelines.instructions.md)
  ├── dto/                                # Request/Response DTOs (see dto-guidelines.instructions.md)
  └── exception/GlobalExceptionHandler.java  # Centralized error handling
src/main/resources/application.properties # Port 8081, springdoc config
src/test/java/com/example/pdf/           # Integration tests
```

## Validated Build & Test Workflow
**Prerequisites**: Java 25, Maven 3.6+ installed

### Standard Development Cycle
```powershell
# 1. Clean build (takes ~30s first run, ~10s cached)
mvnw.cmd clean package

# 2. Run tests (executes in ~15s)
mvnw.cmd test

# 3. Start application (starts in ~8s on port 8081)
mvnw.cmd spring-boot:run

# 4. Verify OpenAPI documentation
# http://localhost:8081/swagger-ui.html
# http://localhost:8081/v3/api-docs
```

### Common Build Issues & Solutions
- **Port 8081 occupied**: Change `server.port` in `application.properties`
- **Maven wrapper fails**: Run `mvn wrapper:wrapper` first
- **Javadoc not appearing in OpenAPI**: Recompile after Javadoc changes - `therapi-runtime-javadoc` processes annotations at compile-time
- **Tests fail**: Ensure no other instance is running on port 8081

## Key Configuration Files
- **pom.xml**: Dependencies (Spring AI BOM, springdoc BOM, therapi-runtime-javadoc)
- **application.properties**: Server port 8081, springdoc package scanning (`com.example.pdf`), OpenAPI 3.1
- **Maven Compiler Plugin**: Configured with `therapi-runtime-javadoc-scribe` annotation processor
- **No external .env files required**: All config in `application.properties`

## Development Conventions

### Package Organization
```
com.example.pdf.controller  → REST endpoints
com.example.pdf.dto         → Request/Response DTOs
com.example.pdf.exception   → Exception handlers
com.example.pdf.config      → Spring configuration
```

### Mandatory Patterns (CRITICAL)
1. **OpenAPI Documentation**: Every controller MUST have `@Tag`, every method MUST have `@Operation` + `@ApiResponses` (see `.github/instructions/controller-guidelines.instructions.md`)
2. **Typed DTOs Only**: NEVER use `Map<String, Object>` - always create proper DTOs with `@Schema`
3. **Javadoc Required**: All public methods need Javadoc - auto-included in OpenAPI via therapi
4. **Centralized Error Handling**: Use `GlobalExceptionHandler`, throw exceptions instead of manual error responses
5. **Integration Tests**: Use `@SpringBootTest` to load full Spring context

## Continuous Integration & Validation
- **No GitHub Actions configured**: Manual validation required before commits
- **Pre-commit checklist**:
  1. Run `mvnw.cmd test` - all tests must pass
  2. Start app and verify Swagger UI at `http://localhost:8081/swagger-ui.html`
  3. Check Javadoc compilation: `target/classes/**/*__Javadoc.json` files should exist
  4. Verify no compilation warnings

## Important Dependencies Behavior
- **therapi-runtime-javadoc**: Extracts Javadoc at compile-time → changes require recompilation
- **springdoc-openapi**: Auto-scans `com.example.pdf` package only (configured in `application.properties`)
- **Spring AI BOM**: Manages Spring AI dependency versions centrally
- **Bean Validation**: Auto-validates `@Valid` request bodies; errors handled by `GlobalExceptionHandler`

## Integration Points & Documentation
- **Spring AI PDF Reader**: https://docs.spring.io/spring-ai/reference/api/etl-pipeline.html#_pdf_page
- **springdoc-openapi**: https://springdoc.org
- **Spring Boot Reference**: https://docs.spring.io/spring-boot/reference/

## Agent Guidelines (IMPORTANT)
⚠️ **Trust these instructions first** - only search the codebase if information here is incomplete or incorrect

**Before making any changes**:
1. Read relevant path-specific instructions in `.github/instructions/` directory
2. Review existing similar files for patterns
3. Run `mvnw.cmd test` after changes to validate

**When adding new features**:
- Controllers → Add to `controller/` + follow `controller-guidelines.instructions.md`
- DTOs → Add to `dto/` + follow `dto-guidelines.instructions.md`  
- Exception handlers → Update `GlobalExceptionHandler.java`
- Tests → Add to `test/` with `@SpringBootTest` pattern
- New API tags → Register in `OpenApiConfig.java`

**Always include**:
- Full OpenAPI annotations on all REST endpoints
- Javadoc on all public methods
- `@Schema` annotations on all DTO fields
- Validation annotations where appropriate
- Integration tests for new endpoints

## Maintaining These Instructions
When the project structure or conventions change:
1. Update this file with new architecture decisions
2. Update path-specific instruction files in `.github/instructions/`
3. Document new dependencies or build steps with exact commands
4. Add new common errors and solutions as discovered
5. Keep instructions under 2 pages - move detailed examples to path-specific files

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
