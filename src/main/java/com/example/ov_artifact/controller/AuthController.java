package com.example.ov_artifact.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ov_artifact.dto.AuthDTO;
import com.example.ov_artifact.services.AuthService;
import com.example.ov_artifact.util.StandardResponse;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<StandardResponse> signUp(@RequestBody AuthDTO authDTO) {
        authService.registerUser(authDTO);
        return new ResponseEntity<>(
                new StandardResponse(true, 201, "User Saved Successfully", null),
                HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<StandardResponse> login(@RequestBody AuthDTO authDTO) {
        AuthDTO loggedUser = authService.loginUser(authDTO);

        return new ResponseEntity<>(
                new StandardResponse(true, 200, "Login Successful!", loggedUser),
                HttpStatus.OK);
    }
}
