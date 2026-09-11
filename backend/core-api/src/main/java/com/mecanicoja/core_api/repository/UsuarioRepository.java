package com.mecanicoja.core_api.repository;

import com.mecanicoja.core_api.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    // Spring Boot vai criar o SQL sozinho para buscar um usuário pelo email
    Optional<Usuario> findByEmail(String email);
}
