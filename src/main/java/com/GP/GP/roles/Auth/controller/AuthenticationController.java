package com.GP.GP.roles.Auth.controller;

import com.GP.GP.roles.Auth.models.request.LoginRequestDTO;
import com.GP.GP.roles.Auth.models.request.PasswordResetRequestDTO;
import com.GP.GP.roles.Auth.models.request.RegisterRequestDTO;
import com.GP.GP.roles.Auth.models.request.PasswordUpdateDTO;
import com.GP.GP.roles.Auth.service.AuthenticationService;
import com.GP.GP.utill.base.BaseResponse;
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
        return authService.login(request);
    }

    @PostMapping("public/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody PasswordResetRequestDTO request) {
        authService.initiatePasswordReset(request.getEmail());
        return ResponseEntity.ok(new BaseResponse(true, "Reset link sent"));
    }

    @PostMapping("public/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordUpdateDTO request) {
        authService.resetPassword(request.getToken(), request.getNewPassword());
        return ResponseEntity.ok(new BaseResponse(true, "Password reset successful"));
    }



}
