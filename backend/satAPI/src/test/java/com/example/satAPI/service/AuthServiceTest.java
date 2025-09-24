package com.example.satAPI.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.satAPI.dto.LoginRequest;
import com.example.satAPI.dto.LoginResponse;
import com.example.satAPI.model.Usuario;

class AuthServiceTest {

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ✅ Cenário 1: Login com sucesso
    @Test
    void deveAutenticarUsuarioComCredenciaisValidas() {
        // Simula um usuário válido retornado pelo serviço
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("user@test.com");
        usuario.setSenha("123456");

        // Mockando comportamento dos serviços externos
        when(usuarioService.buscarPorEmail("user@test.com")).thenReturn(usuario);
        when(jwtService.gerarToken("1")).thenReturn("fake-jwt-token");

        // Cria request de login
        LoginRequest request = new LoginRequest();
        request.setEmail("user@test.com");
        request.setSenha("123456");

        // Executa o método
        LoginResponse response = authService.autenticar(request);

        // Validações
        assertNotNull(response);
        assertEquals("fake-jwt-token", response.getToken());
        assertEquals(usuario, response.getUsuario());

        // Verifica se os mocks foram chamados
        verify(usuarioService).buscarPorEmail("user@test.com");
        verify(jwtService).gerarToken("1");
    }

    // ✅ Cenário 2: Usuário não encontrado
    @Test
    void deveRetornarNullQuandoUsuarioNaoExiste() {
        when(usuarioService.buscarPorEmail("inexistente@test.com")).thenReturn(null);

        LoginRequest request = new LoginRequest();
        request.setEmail("inexistente@test.com");
        request.setSenha("123456");

        LoginResponse response = authService.autenticar(request);

        assertNull(response);
        verify(usuarioService).buscarPorEmail("inexistente@test.com");
        verify(jwtService, never()).gerarToken(anyString());
    }

    // ✅ Cenário 3: Senha incorreta
    @Test
    void deveRetornarNullQuandoSenhaIncorreta() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("user@test.com");
        usuario.setSenha("senha-correta");

        when(usuarioService.buscarPorEmail("user@test.com")).thenReturn(usuario);

        LoginRequest request = new LoginRequest();
        request.setEmail("user@test.com");
        request.setSenha("senha-errada");

        LoginResponse response = authService.autenticar(request);

        assertNull(response);
        verify(usuarioService).buscarPorEmail("user@test.com");
        verify(jwtService, never()).gerarToken(anyString());
    }
}
