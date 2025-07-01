package org.example.babysitting.DTO;

import org.example.babysitting.entities.User;

public class AuthResponseDto {
    private String accessToken;
    private String tokenType = "Bearer";
    //private User user;

    public AuthResponseDto(String accessToken, String tokenType) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }


}
