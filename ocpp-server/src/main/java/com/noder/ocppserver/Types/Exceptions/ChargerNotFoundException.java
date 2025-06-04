package com.noder.ocppserver.Types.Exceptions;

public class ChargerNotFoundException extends RuntimeException {
    public ChargerNotFoundException(String message) {
        super(message);
    }
}