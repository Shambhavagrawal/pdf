package com.example.pdf.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * OpenAPI Configuration for PDF Processing API.
 * 
 * This configuration class defines global API metadata, including title, version,
 * description, contact information, and tag definitions for the OpenAPI documentation.
 * 
 * All REST controllers should use the tags defined here via the @Operation annotation
 * to ensure consistent documentation grouping.
 */
@Configuration
public class OpenApiConfig {

	/**
	 * Configure OpenAPI 3.0 specification with API metadata.
	 * 
	 * @return OpenAPI configuration with title, version, description, and tags
	 */
	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("PDF Processing API")
						.version("1.0.0")
						.description("Spring Boot 3.5.7 application for PDF processing using Spring AI. Provides endpoints for uploading, processing, and retrieving PDF documents with AI capabilities.")
						.contact(new Contact()
								.name("PDF Processing Team")
								.url("https://example.com")
								.email("support@example.com"))
						.license(new License()
								.name("Apache License 2.0")
								.url("https://www.apache.org/licenses/LICENSE-2.0.html")))
				.tags(Arrays.asList(
						new Tag().name("PDF Processing").description("Endpoints for PDF document processing"),
						new Tag().name("Health").description("Application health check endpoints")
				));
	}
}
