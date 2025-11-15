---
applyTo: "**/controller/**/*.java"
---

# REST Controller Development Guidelines

## Mandatory Annotations for Every REST Endpoint

### 1. Controller-Level Annotations
```java
@RestController
@RequestMapping("/api/v1/your-resource")
@Tag(name = "Your Resource", description = "Description of what this controller does")
public class YourResourceController { }
```

### 2. Method-Level Annotations
```java
/**
 * Javadoc describing what this endpoint does.
 * This description will appear in the OpenAPI specification.
 * 
 * @param id the resource identifier
 * @return the resource response
 */
@GetMapping("/{id}")
@Operation(summary = "Short operation summary")
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Successful operation",
                 content = @Content(schema = @Schema(implementation = YourDTO.class))),
    @ApiResponse(responseCode = "404", description = "Resource not found",
                 content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    @ApiResponse(responseCode = "500", description = "Internal server error",
                 content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
})
public ResponseEntity<YourDTO> getResource(
    @PathVariable @Parameter(description = "Resource identifier") String id) {
    // implementation
}
```

## Key Annotation Reference

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
| `@Valid` | Parameter | Enables JSR-303 validation on request bodies |

## Response Code Standards
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

## Documentation Examples

### Example 1: File Upload Endpoint
```java
@PostMapping("/upload")
@Operation(summary = "Upload PDF document")
@ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "PDF uploaded successfully",
                 content = @Content(schema = @Schema(implementation = PdfUploadResponse.class))),
    @ApiResponse(responseCode = "400", description = "Invalid file format or size exceeds limit",
                 content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    @ApiResponse(responseCode = "500", description = "Processing error",
                 content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
})
public ResponseEntity<PdfUploadResponse> uploadPdf(
    @RequestPart("file") @Parameter(description = "PDF file to upload") MultipartFile file) {
    // implementation
}
```

### Example 2: Query with Validation
```java
@GetMapping("/search")
@Operation(summary = "Search PDF documents")
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Search results returned",
                 content = @Content(schema = @Schema(implementation = PdfDocumentDTO.class))),
    @ApiResponse(responseCode = "400", description = "Invalid search parameters",
                 content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
})
public ResponseEntity<List<PdfDocumentDTO>> search(
    @RequestParam @Parameter(description = "Search query string") String query,
    @RequestParam(defaultValue = "0") @Parameter(description = "Page number (0-indexed)") int page,
    @RequestParam(defaultValue = "10") @Parameter(description = "Results per page") int size) {
    // implementation
}
```

### Example 3: POST with Request Body
```java
@PostMapping
@Operation(summary = "Create new resource")
@ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Resource created successfully",
                 content = @Content(schema = @Schema(implementation = ResourceDTO.class))),
    @ApiResponse(responseCode = "400", description = "Validation error",
                 content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
})
public ResponseEntity<ResourceDTO> createResource(
    @Valid @RequestBody @Parameter(description = "Resource data") ResourceRequest request) {
    // implementation
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
}
```

## Important Notes
- **Javadoc is mandatory**: Every controller method must have Javadoc describing its purpose. These comments are automatically introspected and included in OpenAPI specifications via `therapi-runtime-javadoc`
- **Use @Parameter for all parameters**: Even simple parameters should be documented with descriptions
- **Content schema in responses**: Always include `content = @Content(schema = @Schema(implementation = YourDTO.class))` in `@ApiResponse` annotations
- **Keep descriptions concise**: Summaries should be 1-2 sentences; use description for longer explanations
- **Error responses**: All errors are handled by `GlobalExceptionHandler` and return `ErrorResponse` DTO with proper HTTP status codes
- **Use typed DTOs**: Never return raw `Map<String, Object>` - always create proper DTOs with `@Schema` annotations

## Available Tags
When creating new controllers, use existing tags or define new ones in `OpenApiConfig.java`:
- **Greeting**: For greeting and test endpoints
- **PDF Processing**: For PDF upload, processing, and document operations

Whenever adding new functionality, update the tags in `OpenApiConfig.java` if a new category is needed.

## Validation
- Use `@Valid` on request bodies to enable automatic JSR-303 validation
- Validation errors are automatically handled by `GlobalExceptionHandler`
- Common validation annotations: `@NotNull`, `@NotBlank`, `@Size`, `@Min`, `@Max`, `@Email`, `@Pattern`

## Self-Maintenance Instructions
When adding new controllers to this project:
1. Always follow the patterns shown in existing controllers (e.g., `GreetingController.java`)
2. If you discover a new pattern or best practice, update this file with examples
3. If OpenAPI documentation standards change, update the examples in this file
4. Keep examples realistic and based on actual project requirements
5. Document any project-specific conventions (e.g., naming patterns, response wrapping)
