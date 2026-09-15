package com.mecanicoja.core_api.service;

import com.mecanicoja.core_api.domain.Diagnostico;
import com.mecanicoja.core_api.domain.Usuario;
import com.mecanicoja.core_api.domain.Veiculo;
import com.mecanicoja.core_api.dto.DiagnosticoRequest;
import com.mecanicoja.core_api.repository.DiagnosticoRepository;
import com.mecanicoja.core_api.repository.UsuarioRepository;
import com.mecanicoja.core_api.repository.VeiculoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DiagnosticoService {

    private final DiagnosticoRepository diagnosticoRepository;
    private final UsuarioRepository usuarioRepository;
    private final VeiculoRepository veiculoRepository;

    public DiagnosticoService(DiagnosticoRepository diagnosticoRepository, UsuarioRepository usuarioRepository, VeiculoRepository veiculoRepository) {
        this.diagnosticoRepository = diagnosticoRepository;
        this.usuarioRepository = usuarioRepository;
        this.veiculoRepository = veiculoRepository;
    }

    public Diagnostico criarDiagnostico(DiagnosticoRequest request) {
        Usuario motorista = usuarioRepository.findById(request.getMotoristaId()).orElseThrow(() -> new RuntimeException("Motorista não encontrado!"));

        Veiculo veiculo = veiculoRepository.findById(request.getVeiculoId()).orElseThrow(() -> new RuntimeException("Veículo não encontrado!"));

        Diagnostico diagnostico = new Diagnostico();
        diagnostico.setMotorista(motorista);
        diagnostico.setVeiculo(veiculo);
        diagnostico.setSintomasRelatados(request.getSintomasRelatados());
        diagnostico.setCriadoEm(LocalDateTime.now());

        return diagnosticoRepository.save(diagnostico);
    }
}
