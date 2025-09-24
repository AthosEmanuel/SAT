package com.example.satAPI.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.satAPI.model.Produto;
import com.example.satAPI.service.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public List<Produto> listarProdutos() {
        return produtoService.listarTodos();
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<Produto> listarProdutosPorUsuario(@PathVariable Long usuarioId) {
        return produtoService.listarPorUsuario(usuarioId);
    }
}
