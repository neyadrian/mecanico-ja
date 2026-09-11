package com.mecanicoja.core_api.repository;

import com.mecanicoja.core_api.domain.Usuario;
import com.mecanicoja.core_api.domain.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface VeiculoRepository extends JpaRepository<Veiculo, UUID> {

    // retorna uma Lista, pois um motorista pode ter mais de um carro
    List<Veiculo> findByMotorista(Usuario motorista);
}
