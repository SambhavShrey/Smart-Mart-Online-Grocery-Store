package com.examly.springapp.exception;

public enum ExceptionMessage {
    VALIDATION_ERROR("Validation failed. Please check the error details."),
    USER_NOT_FOUND("User Not Found"),
    PASSWORD_INCORRECT("Password is Incorrect!!."),   // For BadCredentialsException
    INCORRECT_PASSWORD("Incorrect Password"),           // For IncorrectEmailOrPasswordException
    NOT_FOUND("Not Found");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}