package com.example.satAPI.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.satAPI.dto.UsuarioDTO;
import com.example.satAPI.model.Usuario;
import com.example.satAPI.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Buscar por email, retorna null se não existir
    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElse(null);
    }

    // Criar usuário
    public Usuario criarUsuario(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        return usuarioRepository.save(usuario);
    }

    // Listar todos usuários
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    // Buscar usuário por id, retorna null se não existir
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    // Atualizar usuário
    public Usuario atualizarUsuario(Long id, UsuarioDTO dto) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.setNome(dto.getNome());
            usuario.setEmail(dto.getEmail());
            usuario.setSenha(dto.getSenha());
            return usuarioRepository.save(usuario);
        }
        return null;
    }

    // Deletar usuário
    public void deletarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}
