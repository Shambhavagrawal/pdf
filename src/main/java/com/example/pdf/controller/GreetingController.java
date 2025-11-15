package com.example.pdf.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Greeting Controller.
 * 
 * Provides REST endpoints for greeting functionality.
 * This is a sample controller demonstrating proper OpenAPI documentation practices.
 */
@RestController
@RequestMapping("/api")
@Tag(name = "Greeting", description = "Endpoints for greeting operations")
public class GreetingController {

	/**
	 * Get a greeting message.
	 * 
	 * Returns a simple greeting message. This endpoint is useful for testing
	 * basic API connectivity and verifying the application is responding.
	 * 
	 * @return a greeting message string
	 */
	@GetMapping("/greeting")
	@Operation(summary = "Get a greeting message")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", 
					description = "Greeting message retrieved successfully",
					content = @Content(mediaType = "application/json", 
							schema = @Schema(type = "string", example = "Hare Krishna")))
	})
	public String getGreeting() {
		return "Hare Krishna";
	}
}
