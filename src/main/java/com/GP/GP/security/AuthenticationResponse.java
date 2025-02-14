package com.GP.GP.security;

import lombok.Data;

@Data
public class AuthenticationResponse {
    private String token;
    private String username;
    private int userID;
    private String message;
    private Role role;

    public AuthenticationResponse(String token, String message, String username, int userID , Role role) {
        this.token = token;
        this.message = message;
        this.userID=userID;
        this.username=username;
        this.role=role;
    }

    public AuthenticationResponse(String token, String message) {
        this.token = token;
        this.message = message;
    }


}
