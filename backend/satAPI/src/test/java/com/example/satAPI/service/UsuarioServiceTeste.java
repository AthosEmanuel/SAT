package com.example.satAPI.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.satAPI.dto.UsuarioDTO;
import com.example.satAPI.model.Usuario;
import com.example.satAPI.repository.UsuarioRepository;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBuscarPorEmail_RetornaUsuario() {
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@email.com");

        when(usuarioRepository.findByEmail("teste@email.com")).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.buscarPorEmail("teste@email.com");
        assertNotNull(resultado);
        assertEquals("teste@email.com", resultado.getEmail());
    }

    @Test
    void testBuscarPorEmail_RetornaNull() {
        when(usuarioRepository.findByEmail("naoexiste@email.com")).thenReturn(Optional.empty());

        Usuario resultado = usuarioService.buscarPorEmail("naoexiste@email.com");
        assertNull(resultado);
    }

    @Test
    void testCriarUsuario() {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNome("Nome Teste");
        dto.setEmail("teste@email.com");
        dto.setSenha("123456");

        Usuario usuarioSalvo = new Usuario();
        usuarioSalvo.setNome(dto.getNome());
        usuarioSalvo.setEmail(dto.getEmail());
        usuarioSalvo.setSenha(dto.getSenha());

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioSalvo);

        Usuario resultado = usuarioService.criarUsuario(dto);

        assertNotNull(resultado);
        assertEquals("Nome Teste", resultado.getNome());
        assertEquals("teste@email.com", resultado.getEmail());
    }

    @Test
    void testListarUsuarios() {
        Usuario usuario1 = new Usuario();
        Usuario usuario2 = new Usuario();
        List<Usuario> usuarios = Arrays.asList(usuario1, usuario2);

        when(usuarioRepository.findAll()).thenReturn(usuarios);

        List<Usuario> resultado = usuarioService.listarUsuarios();
        assertEquals(2, resultado.size());
    }

    @Test
    void testBuscarPorId_RetornaUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.buscarPorId(1L);
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void testBuscarPorId_RetornaNull() {
        when(usuarioRepository.findById(2L)).thenReturn(Optional.empty());

        Usuario resultado = usuarioService.buscarPorId(2L);
        assertNull(resultado);
    }

    @Test
    void testAtualizarUsuario_RetornaAtualizado() {
        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setId(1L);
        usuarioExistente.setNome("Antigo");

        UsuarioDTO dto = new UsuarioDTO();
        dto.setNome("Novo");
        dto.setEmail("novo@email.com");
        dto.setSenha("123");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioExistente));
        when(usuarioRepository.save(usuarioExistente)).thenReturn(usuarioExistente);

        Usuario resultado = usuarioService.atualizarUsuario(1L, dto);

        assertNotNull(resultado);
        assertEquals("Novo", resultado.getNome());
        assertEquals("novo@email.com", resultado.getEmail());
    }

    @Test
    void testAtualizarUsuario_RetornaNull() {
        UsuarioDTO dto = new UsuarioDTO();
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        Usuario resultado = usuarioService.atualizarUsuario(1L, dto);
        assertNull(resultado);
    }

    @Test
    void testDeletarUsuario() {
        doNothing().when(usuarioRepository).deleteById(1L);
        usuarioService.deletarUsuario(1L);
        verify(usuarioRepository, times(1)).deleteById(1L);
    }
}
