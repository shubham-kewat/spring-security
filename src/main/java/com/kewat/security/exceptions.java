package com.kewat.security;


import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class exceptions {

    @ExceptionHandler(value = ExpiredJwtException.class)
    public ResponseEntity<String> expiredJwtException(ExpiredJwtException e) {
        System.out.println("ExpiredJwtException");
        return ResponseEntity.status(401).body("JWT token has expired");
    }
}
