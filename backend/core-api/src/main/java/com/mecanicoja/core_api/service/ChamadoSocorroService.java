package com.mecanicoja.core_api.service;

import com.mecanicoja.core_api.domain.ChamadoSocorro;
import com.mecanicoja.core_api.domain.Diagnostico;
import com.mecanicoja.core_api.domain.Usuario;
import com.mecanicoja.core_api.dto.ChamadoSocorroRequest;
import com.mecanicoja.core_api.repository.ChamadoSocorroRepository;
import com.mecanicoja.core_api.repository.DiagnosticoRepository;
import com.mecanicoja.core_api.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ChamadoSocorroService {

    private final ChamadoSocorroRepository chamadoRepository;
    private final UsuarioRepository usuarioRepository;
    private final DiagnosticoRepository diagnosticoRepository;

    public ChamadoSocorroService(ChamadoSocorroRepository chamadoRepository,
                                 UsuarioRepository usuarioRepository,
                                 DiagnosticoRepository diagnosticoRepository) {
        this.chamadoRepository = chamadoRepository;
        this.usuarioRepository = usuarioRepository;
        this.diagnosticoRepository = diagnosticoRepository;
    }

    // AÇÃO 1: Motorista pede socorro
    public ChamadoSocorro abrirChamado(ChamadoSocorroRequest request) {
        Usuario motorista = usuarioRepository.findById(request.getMotoristaId())
                .orElseThrow(() -> new RuntimeException("Motorista não encontrado!"));

        Diagnostico diagnostico = diagnosticoRepository.findById(request.getDiagnosticoId())
                .orElseThrow(() -> new RuntimeException("Diagnóstico não encontrado!"));

        ChamadoSocorro chamado = new ChamadoSocorro();
        chamado.setMotorista(motorista);
        chamado.setDiagnostico(diagnostico);
        chamado.setStatus(ChamadoSocorro.StatusChamado.PENDENTE);
        chamado.setCriadoEm(LocalDateTime.now());
        // O mecânico fica VAZIO de propósito, ninguém atendeu ainda.

        return chamadoRepository.save(chamado);
    }

    // AÇÃO 2: Mecânico aceita o chamado
    public ChamadoSocorro aceitarChamado(UUID chamadoId, UUID mecanicoId) {
        ChamadoSocorro chamado = chamadoRepository.findById(chamadoId)
                .orElseThrow(() -> new RuntimeException("Chamado não encontrado!"));

        Usuario mecanico = usuarioRepository.findById(mecanicoId)
                .orElseThrow(() -> new RuntimeException("Mecânico não encontrado!"));

        // Regra de ouro: Só pode aceitar se estiver PENDENTE
        if (chamado.getStatus() != ChamadoSocorro.StatusChamado.PENDENTE) {
            throw new RuntimeException("Muito tarde! Este chamado não está mais disponível.");
        }

        chamado.setMecanico(mecanico);
        chamado.setStatus(ChamadoSocorro.StatusChamado.ACEITO);

        return chamadoRepository.save(chamado);
    }
}