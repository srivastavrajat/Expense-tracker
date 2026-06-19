package com.rajat.expense_tracker.security;

import com.rajat.expense_tracker.entity.UserEntity;
import com.rajat.expense_tracker.repository.UserRepository;
import com.rajat.expense_tracker.service.AuthService;
import com.rajat.expense_tracker.service.security.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Null;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(UserRepository userRepository,JwtService jwtService){
        this.jwtService=jwtService;
        this.userRepository=userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain
    )
            throws ServletException, IOException {
        System.out.println("JWT FILTER HIT");
        String authHeader=request.getHeader("Authorization");
        System.out.println("Header = " + authHeader);
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        String jwt=authHeader.substring(7);
        try {
            String email = jwtService.extractEmail(jwt);
            System.out.println("Email extracted = " + email);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        String email = jwtService.extractEmail(jwt);
        UserEntity user= userRepository.findByEmail(email).orElse(null);
        System.out.println("Email extracted = " + email);
        if(user!=null && jwtService.isTokenValid(jwt,email)){
            System.out.println("User Authenticated");
            UsernamePasswordAuthenticationToken authToken=
                    new UsernamePasswordAuthenticationToken(user,null, Collections.emptyList());
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authToken);
        }
        filterChain.doFilter(request,response);
        System.out.println("JWT Filter Executed");
        System.out.println("Email: " + email);

    }
}
