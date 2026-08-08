package com.kewat.security.controllers;

import com.kewat.security.entity.RefreshToken;
import com.kewat.security.request.LoginRequest;
import com.kewat.security.response.LoginResponse;
import com.kewat.security.service.CustomUserDetailsService;
import com.kewat.security.service.JwtService;
import com.kewat.security.service.RefreshTokenService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final CustomUserDetailsService customUserDetailsService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//        return LoginResponse.builder()
//                .token(jwtService.generateToken(loginRequest.getUsername())) //for v1 method
//                .token(jwtService.generateToken((UserDetails) Objects.requireNonNull(authentication.getPrincipal()))) //for v2 method
//                .build();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String accessToken = jwtService.generateToken(userDetails);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getUsername());
        return LoginResponse.builder()
                .token(accessToken)
                .refreshToken(refreshToken.getToken())
                .build();
    }

    @PostMapping("/refresh")
    public LoginResponse refreshToken(@RequestParam String refreshToken){
        RefreshToken token = refreshTokenService.verifyExpiration(refreshTokenService.findByToken(refreshToken));
        RefreshToken oldToken = refreshTokenService.verifyExpiration(refreshTokenService.findByToken(refreshToken));
        refreshTokenService.delete(oldToken);
        RefreshToken newToken = refreshTokenService.createRefreshToken(oldToken.getUsername());
        String accessToken = jwtService.generateToken(customUserDetailsService.loadUserByUsername(newToken.getUsername()));
        return LoginResponse.builder()
                .token(accessToken)
                .refreshToken(newToken.getToken())
                .build();
    }

    @GetMapping("/decode")
    public String decodeToken(@RequestParam String token){
        return jwtService.extractClaims(token).toString();
    }
}
