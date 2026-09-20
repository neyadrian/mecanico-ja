package com.mecanicoja.core_api.controller;

import com.mecanicoja.core_api.domain.Usuario;
import com.mecanicoja.core_api.dto.AuthRequest;
import com.mecanicoja.core_api.dto.AuthResponse;
import com.mecanicoja.core_api.repository.UsuarioRepository;
import com.mecanicoja.core_api.security.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final TokenService tokenService;

    public AuthController(UsuarioRepository usuarioRepository, TokenService tokenService) {
        this.usuarioRepository = usuarioRepository;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        // 1. Busca o usuário pelo e-mail
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        // 2. Verifica se a senha bate (Depois vamos adicionar criptografia profissional aqui!)
        if (!usuario.getSenhaHash().equals(request.getSenha())) {
            return ResponseEntity.status(401).body("Senha incorreta!");
        }

        // 3. Se a senha está certa, fabrica o Token JWT
        String token = tokenService.gerarToken(usuario);

        // 4. Devolve o token na resposta
        return ResponseEntity.ok(new AuthResponse(token));
    }
}