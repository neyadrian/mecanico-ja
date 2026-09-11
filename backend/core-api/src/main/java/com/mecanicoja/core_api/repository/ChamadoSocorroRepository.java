package com.mecanicoja.core_api.repository;

import com.mecanicoja.core_api.domain.ChamadoSocorro;
import com.mecanicoja.core_api.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ChamadoSocorroRepository extends JpaRepository<ChamadoSocorro, UUID> {

    // busca todos os chamados abertos por um motorista
    List<ChamadoSocorro> findByMotorista(Usuario motorista);
    // busca os chamados que foram atribuidos a um mecanico específico
    List<ChamadoSocorro> findByMecanico(Usuario mecanico);
}
