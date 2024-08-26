package com.prueba.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.spring.controller.dto.AuthRequestDTO;
import com.prueba.spring.controller.dto.AuthResponseDTO;
import com.prueba.spring.service.impl.UserDetailsServiceImpl;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO authRequestDTO) {

        try {
            AuthResponseDTO authResponseDTO = userDetailsServiceImpl.loginUser(authRequestDTO);
            return new ResponseEntity<>(authResponseDTO, HttpStatus.OK);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AuthResponseDTO(null, e.getMessage(), null, false),
                    HttpStatus.UNAUTHORIZED);
        }

    }
}
