package com.noder.restapi.dtos;

public class UserLoginDTO {
    private String email;
    private String pin;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getEmail() {
        return email;
    }

    public String getPin() {
        return pin;
    }
}
