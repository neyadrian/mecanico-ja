package com.mecanicoja.core_api.repository;

import com.mecanicoja.core_api.domain.PerfilMecanico;
import com.mecanicoja.core_api.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PerfilMecanicoRepository extends JpaRepository<PerfilMecanico, UUID> {

    Optional<PerfilMecanico> findByUsuario(Usuario usuario);
}
