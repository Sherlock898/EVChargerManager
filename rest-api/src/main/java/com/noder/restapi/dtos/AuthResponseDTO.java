package com.noder.restapi.dtos;

public class AuthResponseDTO {
    private String token;
    private final String tokenType = "Bearer";

    public AuthResponseDTO() {}

    public AuthResponseDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
