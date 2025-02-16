package com.dolphin.platform.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dolphin.platform.config.JWTService;
import com.dolphin.platform.dto.LoginResponse;
import com.dolphin.platform.dto.LoginUserDto;
import com.dolphin.platform.dto.UserRequestDto;
import com.dolphin.platform.model.User;
import com.dolphin.platform.service.AuthenticationService;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/auth")
@RestController
@Slf4j
@CrossOrigin
public class AuthenticationController {
	private final JWTService jwtService;
    
    private final AuthenticationService authenticationService;

    public AuthenticationController(JWTService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody UserRequestDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponse loginResponse = LoginResponse.builder().token(jwtToken).expiresIn(jwtService.getExpirationTime()).user(authenticatedUser).build();
        return ResponseEntity.ok(loginResponse);
    }
}
