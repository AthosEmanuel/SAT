package com.example.satAPI.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long clienteId;
    private Long produtoId;
    private Double valor;
    private LocalDateTime dataCriacao;
    private String produtoNome;
    private String produtoDescricao;

}
