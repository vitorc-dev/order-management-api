package br.com.vitorcarvalho.order_management_api.modules.user.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vitorcarvalho.order_management_api.modules.user.dto.LoginRequest;
import br.com.vitorcarvalho.order_management_api.modules.user.dto.LoginResponse;
import br.com.vitorcarvalho.order_management_api.modules.user.service.AuthService;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
public class AuthUserController {
    private final AuthService authService;

    public AuthUserController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest dto) {
        return ResponseEntity.ok(this.authService.login(dto));
    }
    
}
