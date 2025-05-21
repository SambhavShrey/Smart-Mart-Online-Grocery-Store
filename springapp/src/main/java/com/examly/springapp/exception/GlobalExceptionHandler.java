package com.examly.springapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationErrors(MethodArgumentNotValidException e) {
        List<FieldError> errors = e.getBindingResult().getFieldErrors();
        Map<String, String> errorMap = new HashMap<>();
        for (FieldError err : errors) {
            errorMap.put(err.getField(), err.getDefaultMessage());
        }

        ApiResponse response = new ApiResponse(
                HttpStatus.BAD_REQUEST.value(),
                ExceptionMessage.VALIDATION_ERROR.getMessage(),
                errorMap,
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(NoUserFoundException.class)
    public ResponseEntity<ApiResponse> noUserFound(NoUserFoundException e) {
        ApiResponse response = new ApiResponse(
                HttpStatus.NOT_FOUND.value(),
                ExceptionMessage.USER_NOT_FOUND.getMessage(),
                e.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
     @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse> handleBadCredentials(BadCredentialsException e) {
        ApiResponse response = new ApiResponse(
                HttpStatus.FORBIDDEN.value(),
                e.getMessage(),
                ExceptionMessage.PASSWORD_INCORRECT.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }


    @ExceptionHandler(IncorrectEmailOrPasswordException.class)
    public ResponseEntity<ApiResponse> incorrectEmailOrPassword(IncorrectEmailOrPasswordException e) {
        ApiResponse response = new ApiResponse(
                HttpStatus.UNAUTHORIZED.value(),
                ExceptionMessage.INCORRECT_PASSWORD.getMessage(),
                e.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse> notFoundException(NotFoundException e) {
        ApiResponse response = new ApiResponse(
                HttpStatus.NOT_FOUND.value(),
                ExceptionMessage.NOT_FOUND.getMessage(),
                e.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}