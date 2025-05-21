package com.examly.springapp.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data // Generates getters, setters, toString, equals, and hashCode methods
@NoArgsConstructor // Generates a no-argument constructor
@AllArgsConstructor // Generates a constructor with all fields
public class ApiResponse {
    private int statusCode;
    private String message;
    private Object data;
    private LocalDateTime timestamp = LocalDateTime.now();
}
