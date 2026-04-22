package com.ecommerce.application.security.controller;

import com.ecommerce.application.security.dto.request.LoginRequest;
import com.ecommerce.application.security.dto.request.SignUpRequest;
import com.ecommerce.application.security.dto.response.LoginResponse;
import com.ecommerce.application.security.dto.response.SignUpResponse;
import com.ecommerce.application.security.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/public/register")
    public ResponseEntity<SignUpResponse> register(@RequestBody SignUpRequest signUpRequest) throws Throwable {
        SignUpResponse response = authService.register(signUpRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping("/public/authenticate")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = authService.authenticate(loginRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(loginResponse);
    }
}
