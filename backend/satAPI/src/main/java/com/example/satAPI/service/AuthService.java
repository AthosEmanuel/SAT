package com.example.satAPI.service;

import org.springframework.stereotype.Service;

import com.example.satAPI.dto.LoginRequest;
import com.example.satAPI.dto.LoginResponse;
import com.example.satAPI.model.Usuario;

@Service
public class AuthService {

    private final UsuarioService usuarioService;
    private final JwtService jwtService;

    public AuthService(UsuarioService usuarioService, JwtService jwtService) {
        this.usuarioService = usuarioService;
        this.jwtService = jwtService;
    }

    public LoginResponse autenticar(LoginRequest request) {
        Usuario usuario = usuarioService.buscarPorEmail(request.getEmail());

        if (usuario == null || !usuario.getSenha().equals(request.getSenha())) {
            return null;
        }

        String token = jwtService.gerarToken(usuario.getId().toString());
        return new LoginResponse(token, usuario);
    }
}
