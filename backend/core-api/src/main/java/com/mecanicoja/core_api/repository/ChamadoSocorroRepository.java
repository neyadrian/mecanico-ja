package com.mecanicoja.core_api.repository;

import com.mecanicoja.core_api.domain.ChamadoSocorro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ChamadoSocorroRepository extends JpaRepository<ChamadoSocorro, UUID> {

    Optional<ChamadoSocorro> findByChamadoSocorro(ChamadoSocorro cs);
}
