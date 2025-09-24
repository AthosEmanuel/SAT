package com.example.satAPI.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.satAPI.dto.PedidoDTO;
import com.example.satAPI.model.Pedido;
import com.example.satAPI.model.Produto;
import com.example.satAPI.repository.PedidoRepository;
import com.example.satAPI.repository.ProdutoRepository;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private NotificacaoService notificacaoService;

    // Listar pedidos de um usuário
    public List<Pedido> listarPedidosDoUsuario(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }

    // Criar um pedido
    public Pedido criarPedido(Long clienteId, PedidoDTO dto) {
        if (dto.getProdutoId() == null) {
            throw new IllegalArgumentException("ID do produto é obrigatório");
        }

        // Busca o produto no banco
        Produto produto = produtoRepository.findById(dto.getProdutoId())
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

        // Cria o pedido preenchendo nome, descrição e valor
        Pedido pedido = new Pedido();
        pedido.setClienteId(clienteId);
        pedido.setProdutoId(produto.getId());
        pedido.setProdutoNome(produto.getNome());
        pedido.setProdutoDescricao(produto.getDescricao());
        pedido.setValor(produto.getPreco());
        pedido.setDataCriacao(LocalDateTime.now());

        Pedido salvo = pedidoRepository.save(pedido);

        notificacaoService.enviarEmail(clienteId, "Pedido criado com sucesso!");

        return salvo;
    }
}
