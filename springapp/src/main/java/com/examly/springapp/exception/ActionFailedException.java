package com.examly.springapp.exception;

public class ActionFailedException  extends RuntimeException{
    public ActionFailedException(String message){
        super(message);
    }

}
