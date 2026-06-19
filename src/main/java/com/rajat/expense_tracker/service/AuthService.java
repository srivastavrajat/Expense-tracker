package com.rajat.expense_tracker.service;

import com.rajat.expense_tracker.dto.request.LoginRequest;
import com.rajat.expense_tracker.dto.request.RegisterRequest;
import com.rajat.expense_tracker.dto.response.LoginResponse;
import com.rajat.expense_tracker.dto.response.RegisterResponse;
import com.rajat.expense_tracker.entity.UserEntity;
import com.rajat.expense_tracker.enums.Role;
import com.rajat.expense_tracker.exception.EmailAlreadyExistsException;
import com.rajat.expense_tracker.repository.UserRepository;
import com.rajat.expense_tracker.service.security.JwtService;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    public AuthService(UserRepository userRepository,PasswordEncoder passwordEncoder,JwtService jwtService) {
        this.userRepository=userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService=jwtService;
    }
    public RegisterResponse register(RegisterRequest request){
        userRepository.findByEmail(request.email()).ifPresent(user->{throw new
                EmailAlreadyExistsException(request.email());
        });
        UserEntity user=new UserEntity();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(Role.USER);
        UserEntity savedUser=userRepository.save(user);
        return new RegisterResponse(savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getRole());
    }

    public LoginResponse login(LoginRequest request){
        UserEntity user=userRepository.findByEmail(request.email())
        .orElseThrow(
                ()->new RuntimeException("Invalid credentials")
        );

        boolean matches=passwordEncoder.matches(request.password(), user.getPassword());

        if(!matches){
            throw new RuntimeException("Invalid credentials");
        }

        String token= jwtService.generateToken(user.getEmail());

        return new LoginResponse(token);

    }
}
