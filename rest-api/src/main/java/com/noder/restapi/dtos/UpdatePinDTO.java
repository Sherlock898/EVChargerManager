package com.noder.restapi.dtos;

import jakarta.validation.constraints.NotBlank;

public class UpdatePinDTO {
    @NotBlank
    private String currentPin;

    @NotBlank
    private String newPin;

    // Getters y setters
    public String getCurrentPin() {
        return currentPin;
    }

    public void setCurrentPin(String currentPin) {
        this.currentPin = currentPin;
    }

    public String getNewPin() {
        return newPin;
    }

    public void setNewPin(String newPin) {
        this.newPin = newPin;
    }
}
