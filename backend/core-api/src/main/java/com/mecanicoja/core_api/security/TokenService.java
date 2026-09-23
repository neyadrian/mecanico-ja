package com.mecanicoja.core_api.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.mecanicoja.core_api.domain.Usuario;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    // Essa é a chave mestre do nosso servidor. Ninguém pode descobrir isso!
    // No futuro, isso vai para o arquivo .env, igual fizemos no Python.
    private final String secret = "mecanicoja-super-secreta-key-123456";

    // Método que fabrica o crachá quando o usuário faz login
    public String gerarToken(Usuario usuario) {
        Algorithm algoritmo = Algorithm.HMAC256(secret);
        return JWT.create()
                .withIssuer("MecanicoJa-API") // Quem emitiu o crachá
                .withSubject(usuario.getEmail()) // De quem é o crachá (Email)
                .withClaim("id", usuario.getId().toString()) // Informação extra (ID)
                .withClaim("tipo", usuario.getTipoUsuario()) // Motorista ou Mecânico?
                .withExpiresAt(gerarDataExpiracao()) // Data de validade
                .sign(algoritmo);
    }

    // Método que o porteiro vai usar para checar se o crachá é falso
    public String validarToken(String token) {
        Algorithm algoritmo = Algorithm.HMAC256(secret);
        return JWT.require(algoritmo)
                .withIssuer("MecanicoJa-API")
                .build()
                .verify(token)
                .getSubject(); // Retorna o email se estiver tudo ok
    }

    private Instant gerarDataExpiracao() {
        // O crachá (token) expira em 2 horas por segurança
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}