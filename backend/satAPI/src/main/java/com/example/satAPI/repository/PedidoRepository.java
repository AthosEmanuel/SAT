package com.example.satAPI.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.satAPI.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByClienteId(Long clienteId);
}