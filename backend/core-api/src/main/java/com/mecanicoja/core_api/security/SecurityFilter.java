package com.mecanicoja.core_api.security;

import com.mecanicoja.core_api.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UsuarioRepository repository;

    public SecurityFilter(TokenService tokenService, UsuarioRepository repository) {
        this.tokenService = tokenService;
        this.repository = repository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. Tenta pegar o token do cabeçalho da requisição
        String token = recuperarToken(request);

        if (token != null) {
            // 2. Se tem token, valida e pega o e-mail que está dentro dele
            String email = tokenService.validarToken(token);

            // 3. Puxa o usuário do banco
            var usuario = repository.findByEmail(email).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            // 4. Avisa o Spring Security: "O porteiro conferiu, esse cara tá logado e liberado!"
            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 5. Deixa a requisição seguir o fluxo
        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null; // Se não mandou token, ou mandou no formato errado, recusa
        }
        return authHeader.replace("Bearer ", "");
    }
}