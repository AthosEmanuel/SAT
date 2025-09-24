package com.example.satAPI.config;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.satAPI.model.Produto;
import com.example.satAPI.repository.ProdutoRepository;

@Component
public class ProdutoDataLoader implements CommandLineRunner {

    private final ProdutoRepository produtoRepository;

    public ProdutoDataLoader(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (produtoRepository.count() == 0) {
            produtoRepository.saveAll(List.of(
                    new Produto("Teclado Mecânico", "Teclado mecânico RGB", 350.0, LocalDateTime.now()),
                    new Produto("Mouse Gamer", "Mouse gamer com sensor de 16000 DPI", 180.0, LocalDateTime.now()),
                    new Produto("Monitor 24\"", "Monitor Full HD 144Hz", 1200.0, LocalDateTime.now()),
                    new Produto("Cadeira Gamer", "Cadeira ergonômica com reclinação", 900.0, LocalDateTime.now()),
                    new Produto("Headset", "Headset com som surround 7.1", 250.0, LocalDateTime.now()),
                    new Produto("Placa de Vídeo", "GPU RTX 4070 12GB", 4200.0, LocalDateTime.now()),
                    new Produto("SSD 1TB", "SSD NVMe 1TB", 600.0, LocalDateTime.now()),
                    new Produto("Processador", "Intel Core i7 12700K", 1700.0, LocalDateTime.now()),
                    new Produto("Gabinete", "Gabinete ATX com RGB", 450.0, LocalDateTime.now()),
                    new Produto("Fonte 750W", "Fonte modular 80 Plus Gold", 500.0, LocalDateTime.now())));
        }
    }
}
