package com.rajat.expense_tracker.controller;

import com.rajat.expense_tracker.dto.request.CreateUserRequest;
import com.rajat.expense_tracker.dto.request.LoginRequest;
import com.rajat.expense_tracker.dto.request.RegisterRequest;
import com.rajat.expense_tracker.dto.response.LoginResponse;
import com.rajat.expense_tracker.dto.response.RegisterResponse;
import com.rajat.expense_tracker.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    public AuthController(AuthService authService){
        this.authService=authService;
    }

    @PostMapping("/register")
    public RegisterResponse register(@Valid @RequestBody RegisterRequest request){
        return authService.register(request);
    }
    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request){
        return authService.login(request);
    }
}
