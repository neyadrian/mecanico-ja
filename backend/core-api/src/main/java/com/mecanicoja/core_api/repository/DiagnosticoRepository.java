package com.mecanicoja.core_api.repository;

import com.mecanicoja.core_api.domain.Diagnostico;
import com.mecanicoja.core_api.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DiagnosticoRepository extends JpaRepository<Diagnostico, UUID> {

    // lista de históricos de diagnósticos de um motorista
    List<Diagnostico> findByMotorista(Usuario motorista);
}
