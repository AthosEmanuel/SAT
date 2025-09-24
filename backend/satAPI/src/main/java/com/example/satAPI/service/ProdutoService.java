package com.example.satAPI.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.satAPI.model.Produto;
import com.example.satAPI.repository.ProdutoRepository;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public List<Produto> listarPorUsuario(Long usuarioId) {
        return produtoRepository.findByUsuarioId(usuarioId);
    }
}
