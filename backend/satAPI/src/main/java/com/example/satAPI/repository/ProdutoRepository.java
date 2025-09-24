package com.example.satAPI.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.satAPI.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByUsuarioId(Long usuarioId);

}
