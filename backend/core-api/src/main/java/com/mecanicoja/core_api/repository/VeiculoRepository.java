package com.mecanicoja.core_api.repository;

import com.mecanicoja.core_api.domain.Usuario;
import com.mecanicoja.core_api.domain.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface VeiculoRepository extends JpaRepository<Veiculo, UUID> {

    Optional<Veiculo> findByNome(Veiculo veiculo);
}
