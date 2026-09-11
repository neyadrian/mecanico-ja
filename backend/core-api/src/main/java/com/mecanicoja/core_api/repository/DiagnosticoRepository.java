package com.mecanicoja.core_api.repository;

import com.mecanicoja.core_api.domain.Diagnostico;
import com.mecanicoja.core_api.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DiagnosticoRepository extends JpaRepository<Diagnostico, UUID> {

    Optional<Diagnostico> findByUsuario(Usuario usuario);
}
