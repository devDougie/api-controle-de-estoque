package com.api.inventory_control.dto.auth;

public class TokenResponseDTO {

    private String token;

    public TokenResponseDTO(String token) {
        this.token = token;
    }

    public String getToken() { return token; }
}