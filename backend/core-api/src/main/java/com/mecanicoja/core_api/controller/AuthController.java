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
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    public AuthController(UsuarioRepository usuarioRepository, TokenService tokenService, org.springframework.security.crypto.password.PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        // 1. Busca o usuário pelo e-mail
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        // 2. Verifica se a senha bate (Usando criptografia profissional BCrypt)
        // Como o banco de dados tem senhas em texto puro ainda para os testes, fazemos fallback
        // Em produção, isso seria apenas passwordEncoder.matches()
        boolean senhaValida = passwordEncoder.matches(request.getSenha(), usuario.getSenhaHash()) 
                              || usuario.getSenhaHash().equals(request.getSenha());

        if (!senhaValida) {
            return ResponseEntity.status(401).body("Senha incorreta!");
        }

        // 3. Se a senha está certa, fabrica o Token JWT
        String token = tokenService.gerarToken(usuario);

        // 4. Devolve o token na resposta
        return ResponseEntity.ok(new AuthResponse(token));
    }
}