package com.mecanicoja.core_api.service;

import com.mecanicoja.core_api.domain.Usuario;
import com.mecanicoja.core_api.domain.Veiculo;
import com.mecanicoja.core_api.repository.UsuarioRepository;
import com.mecanicoja.core_api.repository.VeiculoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;
    private final UsuarioRepository usuarioRepository; // precisamos de um repositorio de usuário para buscar o dono

    public VeiculoService(VeiculoRepository veiculoRepository, UsuarioRepository usuarioRepository) {
        this.veiculoRepository = veiculoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // regra, só cadastra veiculo se existir o motorista
    public Veiculo cadastrarVeiculo(UUID motoristaId, Veiculo veiculo){
        Usuario motorista = usuarioRepository.findById(motoristaId).orElseThrow(() -> new RuntimeException("Motorista não encontrado!"));

        veiculo.setMotorista(motorista); // vincula o dono ao carro
        return veiculoRepository.save(veiculo);
    }

    // regra, lista apenas os carros daquele motorista em específico
    public List<Veiculo> buscarVeiculos(UUID motoristaId){
        Usuario motorista = usuarioRepository.findById(motoristaId).orElseThrow(() -> new RuntimeException("Motorista não encontrado!"));

        return veiculoRepository.findByMotorista(motorista);
    }
}
