package com.example.pdf.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Generic API error response DTO.
 * 
 * Represents error responses returned by the API for failed requests.
 */
@Schema(description = "Error response containing error details and HTTP status information")
public class ErrorResponse {

	@Schema(description = "HTTP status code", example = "400")
	private int status;

	@Schema(description = "Error message describing the issue", example = "Invalid request")
	private String message;

	@Schema(description = "Timestamp of when the error occurred (ISO 8601 format)", example = "2025-11-15T10:30:00Z")
	private String timestamp;

	@Schema(description = "API path that resulted in the error", example = "/api/v1/pdf/health")
	private String path;

	/**
	 * Default constructor for JSON deserialization.
	 */
	public ErrorResponse() {
	}

	/**
	 * Constructor with status, message, and timestamp.
	 * 
	 * @param status the HTTP status code
	 * @param message the error message
	 * @param timestamp the error timestamp
	 */
	public ErrorResponse(int status, String message, String timestamp) {
		this.status = status;
		this.message = message;
		this.timestamp = timestamp;
	}

	/**
	 * Constructor with all parameters.
	 * 
	 * @param status the HTTP status code
	 * @param message the error message
	 * @param timestamp the error timestamp
	 * @param path the API path
	 */
	public ErrorResponse(int status, String message, String timestamp, String path) {
		this.status = status;
		this.message = message;
		this.timestamp = timestamp;
		this.path = path;
	}

	// Getters and Setters
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
