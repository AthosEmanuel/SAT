package com.example.satAPI.service;

import org.springframework.stereotype.Service;

@Service
public class NotificacaoService {
    public void enviarEmail(Long clienteId, String mensagem) {
        System.out.println("Enviando email para cliente " + clienteId + ": " + mensagem);
    }
}
