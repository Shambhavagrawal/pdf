package com.example.pdf.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Greeting response DTO containing a message")
public class GreetingResponse {

    @Schema(description = "Greeting message", example = "Hare Krishna")
    private String message;

    public GreetingResponse() {
    }

    public GreetingResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
