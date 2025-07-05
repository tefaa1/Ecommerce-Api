package com.ecommerce_api.demo.controller;

import com.ecommerce_api.demo.model.dto.request.LoginRequestDTO;
import com.ecommerce_api.demo.model.dto.request.RefreshRequest;
import com.ecommerce_api.demo.model.dto.request.RegisterRequestDTO;
import com.ecommerce_api.demo.model.dto.response.AuthenticationResponse;
import com.ecommerce_api.demo.model.dto.response.UserResponseDTO;
import com.ecommerce_api.demo.model.entity.User;
import com.ecommerce_api.demo.model.mapper.UserMapper;
import com.ecommerce_api.demo.repository.UserRepository;
import com.ecommerce_api.demo.security.JWTUtil;
import com.ecommerce_api.demo.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {

        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDTO request) {

        userService.register(request);
        return ResponseEntity.ok("Account registered successfully. Please log in.");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO request) {

        AuthenticationResponse authenticationResponse = userService.login(request);
        return ResponseEntity.ok().body(authenticationResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@Valid @RequestBody RefreshRequest request) {

        AuthenticationResponse auth = userService.refreshAccessToken(request.getRefreshToken());
        return ResponseEntity.ok().body(auth);
    }

}
