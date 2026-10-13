package com.mecanicoja.core_api.service;

import com.mecanicoja.core_api.domain.Usuario;
import com.mecanicoja.core_api.dto.RegistroRequest;
import com.mecanicoja.core_api.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    // Spring injeta o repositório e o encoder automaticamente
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registra um novo usuário a partir de um DTO seguro.
     * A senha é SEMPRE criptografada com BCrypt antes de chegar ao banco.
     * Nunca mais salvaremos senhas em texto puro!
     */
    public Usuario registrarUsuario(RegistroRequest request) {
        // Verifica se já tem alguém com esse e-mail no banco
        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Este e-mail já está em uso!");
        }

        // Monta o objeto de domínio a partir do DTO
        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(request.getNome());
        novoUsuario.setEmail(request.getEmail());
        novoUsuario.setTelefone(request.getTelefone());
        novoUsuario.setTipoUsuario(request.getTipoUsuario());
        novoUsuario.setCriadoEm(LocalDateTime.now());

        // 🔐 A mágica acontece aqui: criptografa a senha com BCrypt!
        novoUsuario.setSenhaHash(passwordEncoder.encode(request.getSenha()));

        return usuarioRepository.save(novoUsuario);
    }

    // Regra de negócio: listar todos os usuários
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }
}

