package com.example.satAPI.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.satAPI.model.Usuario;
import com.example.satAPI.service.JwtService;
import com.example.satAPI.service.UsuarioService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UsuarioService usuarioService;

    public JwtAuthenticationFilter(JwtService jwtService, UsuarioService usuarioService) {
        this.jwtService = jwtService;
        this.usuarioService = usuarioService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {
                String userId = jwtService.extrairUserId(token);
                Usuario usuario = usuarioService.buscarPorId(Long.parseLong(userId));

                if (usuario != null) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            usuario,
                            null,
                            usuario.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            } catch (Exception e) {
                System.out.println("Token inválido: " + e.getMessage());
            }
        }

        chain.doFilter(request, response);
    }
}
