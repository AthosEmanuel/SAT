package com.example.satAPI.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.satAPI.dto.PedidoDTO;
import com.example.satAPI.model.Pedido;
import com.example.satAPI.model.Produto;
import com.example.satAPI.repository.PedidoRepository;
import com.example.satAPI.repository.ProdutoRepository;

class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private NotificacaoService notificacaoService;

    @InjectMocks
    private PedidoService pedidoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveListarPedidosDeUmUsuario() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setClienteId(10L);

        when(pedidoRepository.findByClienteId(10L)).thenReturn(List.of(pedido));

        List<Pedido> pedidos = pedidoService.listarPedidosDoUsuario(10L);

        assertNotNull(pedidos);
        assertEquals(1, pedidos.size());
        assertEquals(10L, pedidos.get(0).getClienteId());

        verify(pedidoRepository).findByClienteId(10L);
    }

    @Test
    void deveCriarPedidoComSucesso() {

        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Teclado Mecânico");
        produto.setDescricao("Switch Blue RGB");
        produto.setPreco(299.90);

        // Mock do DTO enviado ao serviço
        PedidoDTO dto = new PedidoDTO();
        dto.setProdutoId(1L);

        // Pedido salvo simulado
        Pedido pedidoSalvo = new Pedido();
        pedidoSalvo.setId(100L);
        pedidoSalvo.setClienteId(10L);
        pedidoSalvo.setProdutoNome("Teclado Mecânico");
        pedidoSalvo.setDataCriacao(LocalDateTime.now());

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoSalvo);

        Pedido resultado = pedidoService.criarPedido(10L, dto);

        assertNotNull(resultado);
        assertEquals(100L, resultado.getId());
        assertEquals("Teclado Mecânico", resultado.getProdutoNome());
        verify(produtoRepository).findById(1L);
        verify(pedidoRepository).save(any(Pedido.class));
        verify(notificacaoService).enviarEmail(10L, "Pedido criado com sucesso!");
    }

    @Test
    void deveLancarErroSeProdutoIdForNulo() {
        PedidoDTO dto = new PedidoDTO();
        dto.setProdutoId(null);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> pedidoService.criarPedido(10L, dto));

        assertEquals("ID do produto é obrigatório", ex.getMessage());
        verifyNoInteractions(produtoRepository, pedidoRepository, notificacaoService);
    }

    @Test
    void deveLancarErroSeProdutoNaoForEncontrado() {
        PedidoDTO dto = new PedidoDTO();
        dto.setProdutoId(99L);

        when(produtoRepository.findById(99L)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> pedidoService.criarPedido(10L, dto));

        assertEquals("Produto não encontrado", ex.getMessage());
        verify(produtoRepository).findById(99L);
        verify(pedidoRepository, never()).save(any());
        verify(notificacaoService, never()).enviarEmail(anyLong(), anyString());
    }
}
