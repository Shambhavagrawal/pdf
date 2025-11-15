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
  5. If API endpoints, dependencies, or structure changed: update related docs & `.github/instructions/documentation-maintenance.instructions.md` per Documentation Maintenance section

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

### Documentation Maintenance
Refer to `.github/instructions/documentation-maintenance.instructions.md` whenever you:
- Add or modify REST endpoints (controllers, DTOs, tags)
- Introduce new modules, packages, or configuration classes
- Add or change build/dependency steps in `pom.xml`
- Alter error handling patterns or response schemas

Required actions in the same commit:
1. Update `README.md` / help guides with new endpoints or features.
2. Adjust instruction files in `.github/instructions/` if conventions shift.
3. Re-run `mvnw.cmd clean package` to regenerate Javadoc metadata.
4. Verify Swagger UI reflects changes (`http://localhost:8081/swagger-ui.html`).
5. Confirm new or changed DTOs have `@Schema` and validation annotations.

Skip this section only for pure internal refactors that do not change public API, dependencies, or documented behavior.

#### Alignment with Latest Copilot Guidance
At least once per release cycle (or when prompted by significant GitHub Copilot guideline updates), compare the current `.github/instructions/documentation-maintenance.instructions.md` and this file against the latest published Copilot recommendations. Update terminology, process steps, and checklists where they diverge. Record date of review in the maintenance file's metadata (Last updated / Next review).
