package br.com.vitorcarvalho.order_management_api.modules.user.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import br.com.vitorcarvalho.order_management_api.modules.exceptions.InvalidCredentialException;
import br.com.vitorcarvalho.order_management_api.modules.jwt.JWTService;
import br.com.vitorcarvalho.order_management_api.modules.user.dto.LoginRequest;
import br.com.vitorcarvalho.order_management_api.modules.user.dto.LoginResponse;

@Service 
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public AuthService(AuthenticationManager authenticationManager, JWTService jwtService){
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest dto){
        try{
            Authentication auth = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
            );
            return new LoginResponse(this.jwtService.generateToken(auth.getName()));
        }catch(AuthenticationException ex){
            throw new InvalidCredentialException("Invalid credentials.");
        }
    }
}
