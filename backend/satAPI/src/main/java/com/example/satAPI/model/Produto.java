package com.example.satAPI.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private Double preco;
    private LocalDateTime dataCriacao = LocalDateTime.now();
    private Long usuarioId;

    public Produto() {
    }

    public Produto(String nome, String descricao, Double preco, LocalDateTime dataCriacao) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.dataCriacao = dataCriacao;
    }
}
