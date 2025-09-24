package com.example.satAPI.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.satAPI.dto.PedidoDTO;
import com.example.satAPI.model.Pedido;
import com.example.satAPI.model.Usuario;
import com.example.satAPI.service.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<?> criarPedido(@RequestBody PedidoDTO dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || auth.getPrincipal() == null || !(auth.getPrincipal() instanceof Usuario usuario)) {
            return ResponseEntity.status(401).body("Usuário não autenticado");
        }

        Pedido pedido = pedidoService.criarPedido(usuario.getId(), dto);
        return ResponseEntity.ok(pedido);
    }

    @GetMapping
    public ResponseEntity<?> listarPedidosUsuario() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || auth.getPrincipal() == null) {
            return ResponseEntity.status(401).body("Usuário não autenticado");
        }
        Long usuarioId;

        try {
            usuarioId = Long.parseLong(auth.getName());
        } catch (NumberFormatException e) {
            return ResponseEntity.status(400).body("ID do usuário inválido no token");
        }

        List<Pedido> pedidos = pedidoService.listarPedidosDoUsuario(usuarioId);
        return ResponseEntity.ok(pedidos);
    }

}
