---
applyTo: "**/dto/**/*.java"
---

# DTO Development Guidelines

## Mandatory Annotations for Every DTO

### 1. Class-Level Annotations
```java
@Schema(description = "Data Transfer Object for Your Resource")
public class YourDTO {
    // fields
}
```

### 2. Field-Level Annotations
```java
@Schema(description = "Unique identifier", example = "123")
private String id;

@Schema(description = "Resource name", example = "Example Name", required = true)
@NotBlank(message = "Name cannot be blank")
private String name;

@Schema(description = "Email address", example = "user@example.com")
@Email(message = "Must be a valid email address")
private String email;

@Schema(description = "Age in years", example = "25", minimum = "0", maximum = "150")
@Min(value = 0, message = "Age cannot be negative")
@Max(value = 150, message = "Age must be realistic")
private Integer age;
```

## Key Annotation Reference

| Annotation | Location | Purpose |
|---|---|---|
| `@Schema` | Class/Field | Defines data model structure and OpenAPI documentation |
| `@NotNull` | Field | Field cannot be null |
| `@NotBlank` | Field | String field cannot be null, empty, or whitespace |
| `@NotEmpty` | Field | Collection/Array cannot be empty |
| `@Size` | Field | Validates string length or collection size |
| `@Min` / `@Max` | Field | Validates numeric minimum/maximum values |
| `@Email` | Field | Validates email format |
| `@Pattern` | Field | Validates against regex pattern |
| `@Valid` | Field | Enables nested validation on complex objects |

## DTO Examples

### Example 1: Request DTO with Validation
```java
package com.example.pdf.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(description = "Request DTO for creating a new PDF document")
public class PdfDocumentRequest {
    
    @Schema(description = "Document title", example = "Annual Report 2024", required = true)
    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters")
    private String title;
    
    @Schema(description = "Document description", example = "Financial annual report")
    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;
    
    @Schema(description = "Document category", example = "FINANCIAL", 
            allowableValues = {"FINANCIAL", "LEGAL", "TECHNICAL", "OTHER"})
    @NotNull(message = "Category is required")
    private DocumentCategory category;
    
    @Schema(description = "Document tags for classification", example = "[\"report\", \"2024\"]")
    @Size(max = 10, message = "Cannot have more than 10 tags")
    private List<@NotBlank String> tags;
    
    // Getters and setters
}
```

### Example 2: Response DTO
```java
package com.example.pdf.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Response DTO containing PDF document information")
public class PdfDocumentResponse {
    
    @Schema(description = "Unique document identifier", example = "doc-123456")
    private String documentId;
    
    @Schema(description = "Document title", example = "Annual Report 2024")
    private String title;
    
    @Schema(description = "Upload timestamp", example = "2024-01-15T10:30:00")
    private LocalDateTime uploadedAt;
    
    @Schema(description = "Document size in bytes", example = "2048576")
    private Long sizeInBytes;
    
    @Schema(description = "Processing status", 
            example = "COMPLETED",
            allowableValues = {"PENDING", "PROCESSING", "COMPLETED", "FAILED"})
    private ProcessingStatus status;
    
    // Getters and setters
}
```

### Example 3: Error Response DTO
```java
package com.example.pdf.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Error response returned when request fails")
public class ErrorResponse {
    
    @Schema(description = "HTTP status code", example = "400")
    private int status;
    
    @Schema(description = "Error message", example = "Validation failed")
    private String message;
    
    @Schema(description = "Detailed error description", example = "Field 'title' is required")
    private String details;
    
    @Schema(description = "Timestamp when error occurred", example = "2024-01-15T10:30:00")
    private LocalDateTime timestamp;
    
    @Schema(description = "Request path that caused the error", example = "/api/v1/documents")
    private String path;
    
    @Schema(description = "List of validation errors", nullable = true)
    private List<ValidationError> validationErrors;
    
    // Getters and setters
}
```

### Example 4: Nested DTO with Validation
```java
package com.example.pdf.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Document metadata with nested author information")
public class DocumentMetadata {
    
    @Schema(description = "Document identifier", example = "doc-123")
    private String documentId;
    
    @Schema(description = "Author information", required = true)
    @NotNull(message = "Author information is required")
    @Valid // Enables validation on nested object
    private AuthorInfo author;
    
    // Getters and setters
}

@Schema(description = "Author information")
class AuthorInfo {
    
    @Schema(description = "Author name", example = "John Doe", required = true)
    @NotBlank(message = "Author name is required")
    private String name;
    
    @Schema(description = "Author email", example = "john@example.com")
    @Email(message = "Must be a valid email")
    private String email;
    
    // Getters and setters
}
```

## Important Notes

### Schema Annotation Properties
- **description**: Human-readable description of the field
- **example**: Example value for documentation and testing
- **required**: Whether field is required (also driven by validation annotations)
- **minimum/maximum**: For numeric constraints
- **minLength/maxLength**: For string length constraints
- **allowableValues**: For enum-like fields
- **nullable**: Whether field can be null (default: false)
- **deprecated**: Mark deprecated fields

### Validation Best Practices
1. **Combine @Schema with validation annotations**: `@Schema` documents the constraint, validation annotation enforces it
2. **Use appropriate validation**: Don't use `@NotBlank` on numeric fields, use `@NotNull` instead
3. **Provide clear error messages**: Custom messages help API consumers understand validation failures
4. **Validate collections**: Use `@Valid` on collection elements if they contain nested objects
5. **Use groups for conditional validation**: When different endpoints require different validation rules

### Common Patterns
```java
// Optional field with default
@Schema(description = "Page size", example = "10", defaultValue = "10")
private Integer pageSize = 10;

// Required email field
@Schema(description = "User email", example = "user@example.com", required = true)
@NotBlank(message = "Email is required")
@Email(message = "Must be a valid email address")
private String email;

// Enum field
@Schema(description = "Status", example = "ACTIVE", allowableValues = {"ACTIVE", "INACTIVE", "PENDING"})
@NotNull(message = "Status is required")
private Status status;

// Timestamp field
@Schema(description = "Created timestamp", example = "2024-01-15T10:30:00")
private LocalDateTime createdAt;

// File size field
@Schema(description = "File size in bytes", example = "2048576", minimum = "0")
@Min(value = 0, message = "File size cannot be negative")
private Long fileSize;
```

## Constructor and Builder Patterns
Use appropriate patterns for DTO construction:

```java
// All-args constructor for immutability
public record DocumentDTO(
    @Schema(description = "Document ID") String id,
    @Schema(description = "Title") String title,
    @Schema(description = "Created") LocalDateTime createdAt
) {}

// Builder pattern for complex DTOs
@Schema(description = "Complex document metadata")
public class DocumentMetadata {
    // fields
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder {
        // builder implementation
    }
}
```

## Self-Maintenance Instructions
When adding new DTOs to this project:
1. Always follow the patterns shown in existing DTOs (e.g., `ErrorResponse.java`, `HealthCheckResponse.java`)
2. If you discover a new validation pattern or best practice, update this file with examples
3. If OpenAPI schema documentation standards change, update the examples in this file
4. Keep examples realistic and based on actual project requirements
5. Document any project-specific DTO conventions (e.g., naming patterns, response wrapping patterns)
6. When adding new validation patterns, document them with clear examples
7. Update validation message standards if team preferences change
