package com.example.satAPI.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.satAPI.model.Produto;
import com.example.satAPI.repository.ProdutoRepository;

public class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveListarTodosProdutos() {
        // Arrange - criar produtos falsos
        Produto p1 = new Produto("Produto 1", "Descricao 1", 100.0, LocalDateTime.now());
        Produto p2 = new Produto("Produto 2", "Descricao 2", 200.0, LocalDateTime.now());
        when(produtoRepository.findAll()).thenReturn(List.of(p1, p2));

        // Act - chamar o método
        List<Produto> produtos = produtoService.listarTodos();

        // Assert - validar comportamento
        assertNotNull(produtos);
        assertEquals(2, produtos.size());
        assertEquals("Produto 1", produtos.get(0).getNome());
        verify(produtoRepository, times(1)).findAll();
    }

    @Test
    void deveListarProdutosPorUsuario() {
        // Arrange - simula produtos de um usuário específico
        Long usuarioId = 42L;
        Produto p = new Produto("Produto User", "Descricao User", 300.0, LocalDateTime.now());
        p.setUsuarioId(usuarioId);
        when(produtoRepository.findByUsuarioId(usuarioId)).thenReturn(List.of(p));

        // Act
        List<Produto> produtos = produtoService.listarPorUsuario(usuarioId);

        // Assert
        assertNotNull(produtos);
        assertEquals(1, produtos.size());
        assertEquals(usuarioId, produtos.get(0).getUsuarioId());
        verify(produtoRepository, times(1)).findByUsuarioId(usuarioId);
    }
}
