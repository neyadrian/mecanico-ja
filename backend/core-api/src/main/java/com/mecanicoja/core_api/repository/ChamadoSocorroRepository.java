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
    // busca chamados filtrados por status (ex: listar todos os PENDENTES para o painel de mecânicos)
    List<ChamadoSocorro> findByStatus(ChamadoSocorro.StatusChamado status);
    // busca histórico de um motorista por status específico
    List<ChamadoSocorro> findByMotoristaAndStatus(Usuario motorista, ChamadoSocorro.StatusChamado status);
    // busca histórico de um mecânico por status específico
    List<ChamadoSocorro> findByMecanicoAndStatus(Usuario mecanico, ChamadoSocorro.StatusChamado status);
}
