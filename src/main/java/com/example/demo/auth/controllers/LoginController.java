package com.example.demo.auth.controllers;

import com.example.demo.auth.dto.LoginRequestDto;
import com.example.demo.auth.dto.LoginResponseDto;
import com.example.demo.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/login")
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final AuthService authService;

    @PostMapping
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequest) {
        return authService.authenticate(loginRequest);
    }

}
