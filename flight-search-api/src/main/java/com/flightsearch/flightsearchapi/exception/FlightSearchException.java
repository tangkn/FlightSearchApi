package com.flightsearch.flightsearchapi.exception;

public class FlightSearchException extends Exception{
     private int exceptionCode;

    protected FlightSearchException(int exceptionCode, String message) {
        super(message);
        this.exceptionCode=exceptionCode;
    }
    public int getExceptionCode(){
        return exceptionCode;
    }

}
