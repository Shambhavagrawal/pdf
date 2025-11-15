package com.example.pdf.exception;

import com.example.pdf.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import io.swagger.v3.oas.annotations.Hidden;

import java.time.Instant;

/**
 * Global Exception Handler for REST API.
 * 
 * Centralized exception handling for all REST controllers in the application.
 * Provides consistent error response format with proper HTTP status codes
 * and OpenAPI documentation.
 */
@ControllerAdvice
@Hidden
public class GlobalExceptionHandler {

	/**
	 * Handle generic runtime exceptions.
	 * 
	 * @param ex the exception thrown
	 * @param request the current request context
	 * @return ErrorResponse with status 500 and error details
	 */
	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<ErrorResponse> handleRuntimeException(
			RuntimeException ex, 
			WebRequest request) {
		
		ErrorResponse errorResponse = new ErrorResponse(
				HttpStatus.INTERNAL_SERVER_ERROR.value(),
				ex.getMessage() != null ? ex.getMessage() : "An internal server error occurred",
				Instant.now().toString(),
				request.getDescription(false).replace("uri=", "")
		);
		
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Handle illegal argument exceptions.
	 * 
	 * @param ex the exception thrown
	 * @param request the current request context
	 * @return ErrorResponse with status 400 and error details
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
			IllegalArgumentException ex, 
			WebRequest request) {
		
		ErrorResponse errorResponse = new ErrorResponse(
				HttpStatus.BAD_REQUEST.value(),
				ex.getMessage() != null ? ex.getMessage() : "Invalid argument provided",
				Instant.now().toString(),
				request.getDescription(false).replace("uri=", "")
		);
		
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handle all other exceptions.
	 * 
	 * @param ex the exception thrown
	 * @param request the current request context
	 * @return ErrorResponse with status 500 and error details
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<ErrorResponse> handleGlobalException(
			Exception ex, 
			WebRequest request) {
		
		ErrorResponse errorResponse = new ErrorResponse(
				HttpStatus.INTERNAL_SERVER_ERROR.value(),
				ex.getMessage() != null ? ex.getMessage() : "An unexpected error occurred",
				Instant.now().toString(),
				request.getDescription(false).replace("uri=", "")
		);
		
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
