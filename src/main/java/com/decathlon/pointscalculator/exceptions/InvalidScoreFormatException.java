package com.decathlon.pointscalculator.exceptions;

public class InvalidScoreFormatException extends RuntimeException{
    public InvalidScoreFormatException(String message){
        super(message);
    }
}
