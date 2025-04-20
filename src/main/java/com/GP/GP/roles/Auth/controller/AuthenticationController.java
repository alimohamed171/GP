package com.GP.GP.roles.Auth.controller;

import com.GP.GP.entities.User;
import com.GP.GP.roles.Auth.models.request.LoginRequestDTO;
import com.GP.GP.roles.Auth.models.request.RegisterRequestDTO;
import com.GP.GP.security.AuthenticationResponse;
import com.GP.GP.roles.Auth.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final AuthenticationService authService;

    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }


    @PostMapping("public/register")
    public ResponseEntity<Object> register(
            @RequestBody RegisterRequestDTO request
            ) {
        return ResponseEntity.ok(authService.register(request));
    }


    @PostMapping("public/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }



}
