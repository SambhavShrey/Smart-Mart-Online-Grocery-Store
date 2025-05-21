package com.examly.springapp.exception;

public class IncorrectEmailOrPasswordException extends RuntimeException {
    public IncorrectEmailOrPasswordException(String message){
        super(message);
  }
}
