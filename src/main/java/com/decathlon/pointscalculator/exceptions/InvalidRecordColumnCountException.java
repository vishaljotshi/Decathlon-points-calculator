package com.decathlon.pointscalculator.exceptions;

public class InvalidRecordColumnCountException extends RuntimeException{
    public InvalidRecordColumnCountException(String message) {
        super(message);
    }
}
