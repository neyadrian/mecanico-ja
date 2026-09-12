package com.mecanicoja.core_api.service;

import com.mecanicoja.core_api.domain.Usuario;
import com.mecanicoja.core_api.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    // spring injeta o repositório aqui automaticamente
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // regra de negócio 1, cadastrar usuário
    public Usuario cadastrarUsuario(Usuario usuario){

        // verifica se já tem alguém com esse e-mail no banco
        if(usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new RuntimeException("Este e-mail já está em uso!");
        }

        return usuarioRepository.save(usuario);
    }

    // regra de negócio 2, listar todos
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }
}
