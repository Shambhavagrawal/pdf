package com.example.pdf.controller;

import com.example.pdf.dto.HealthCheckResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * PDF Processing Controller.
 * 
 * Provides REST endpoints for PDF document processing operations using Spring AI.
 * All endpoints in this controller are documented with OpenAPI annotations for
 * automatic Swagger UI documentation generation.
 */
@RestController
@RequestMapping("/api/v1/pdf")
@Tag(name = "PDF Processing", description = "Endpoints for PDF document processing")
public class PdfProcessingController {

	/**
	 * Health check endpoint for the PDF Processing API.
	 * 
	 * Verifies that the application is running and ready to process PDF documents.
	 * This endpoint returns detailed status information including the service name
	 * and current API version.
	 * 
	 * @return ResponseEntity containing health status with HTTP 200 OK
	 */
	@GetMapping("/health")
	@Operation(summary = "Check API health status")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", 
					description = "API is healthy and operational",
					content = @Content(mediaType = "application/json", 
							schema = @Schema(implementation = HealthCheckResponse.class))),
			@ApiResponse(responseCode = "503", 
					description = "API is unavailable",
					content = @Content(mediaType = "application/json",
							schema = @Schema(implementation = HealthCheckResponse.class)))
	})
	public ResponseEntity<HealthCheckResponse> health() {
		HealthCheckResponse response = new HealthCheckResponse(
				"UP",
				"PDF Processing API",
				"1.0.0"
		);
		return ResponseEntity.ok(response);
	}
}
