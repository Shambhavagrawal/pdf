package com.example.pdf.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Health check response DTO.
 * 
 * Contains status information about the PDF Processing API service.
 */
@Schema(description = "Health check response containing service status and version information")
public class HealthCheckResponse {

	@Schema(description = "Current health status of the service", example = "UP")
	private String status;

	@Schema(description = "Name of the service", example = "PDF Processing API")
	private String service;

	@Schema(description = "Current version of the API", example = "1.0.0")
	private String version;

	/**
	 * Default constructor for JSON deserialization.
	 */
	public HealthCheckResponse() {
	}

	/**
	 * Constructor with all parameters.
	 * 
	 * @param status the health status
	 * @param service the service name
	 * @param version the service version
	 */
	public HealthCheckResponse(String status, String service, String version) {
		this.status = status;
		this.service = service;
		this.version = version;
	}

	// Getters and Setters
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
