package com.mecanicoja.core_api.service;

import com.mecanicoja.core_api.domain.ChamadoSocorro;
import com.mecanicoja.core_api.domain.Diagnostico;
import com.mecanicoja.core_api.domain.Usuario;
import com.mecanicoja.core_api.dto.ChamadoSocorroRequest;
import com.mecanicoja.core_api.repository.ChamadoSocorroRepository;
import com.mecanicoja.core_api.repository.DiagnosticoRepository;
import com.mecanicoja.core_api.repository.UsuarioRepository;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ChamadoSocorroService {

    private final ChamadoSocorroRepository chamadoRepository;
    private final UsuarioRepository usuarioRepository;
    private final DiagnosticoRepository diagnosticoRepository;
    private final StringRedisTemplate redisTemplate;

    public ChamadoSocorroService(ChamadoSocorroRepository chamadoRepository,
                                 UsuarioRepository usuarioRepository,
                                 DiagnosticoRepository diagnosticoRepository,
                                 StringRedisTemplate redisTemplate) {
        this.chamadoRepository = chamadoRepository;
        this.usuarioRepository = usuarioRepository;
        this.diagnosticoRepository = diagnosticoRepository;
        this.redisTemplate = redisTemplate;
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

        ChamadoSocorro chamadoSalvo = chamadoRepository.save(chamado);

        // FASE 4: Avisa o Python via Redis que o chamado foi aceito!
        // O Python vai escutar este canal e notificar os WebSockets correspondentes.
        redisTemplate.convertAndSend("canal_chamados", chamadoSalvo.getId().toString() + ":ACEITO");

        return chamadoSalvo;
    }

    // AÇÃO 3: Motorista ou sistema cancela o chamado
    public ChamadoSocorro cancelarChamado(UUID chamadoId) {
        ChamadoSocorro chamado = chamadoRepository.findById(chamadoId)
                .orElseThrow(() -> new RuntimeException("Chamado não encontrado!"));

        // Regra de negócio: não é possível cancelar chamados já concluídos ou já cancelados
        if (chamado.getStatus() == ChamadoSocorro.StatusChamado.CONCLUIDO) {
            throw new RuntimeException("Não é possível cancelar um chamado que já foi concluído!");
        }
        if (chamado.getStatus() == ChamadoSocorro.StatusChamado.CANCELADO) {
            throw new RuntimeException("Este chamado já se encontra cancelado!");
        }

        chamado.setStatus(ChamadoSocorro.StatusChamado.CANCELADO);
        ChamadoSocorro chamadoCancelado = chamadoRepository.save(chamado);

        // Notifica via Redis Pub/Sub que o chamado foi cancelado
        redisTemplate.convertAndSend("canal_chamados", chamadoCancelado.getId().toString() + ":CANCELADO");

        return chamadoCancelado;
    }

    // LISTAGEM 1: Listar chamados com filtro opcional por status
    public List<ChamadoSocorro> listarChamados(ChamadoSocorro.StatusChamado status) {
        if (status != null) {
            return chamadoRepository.findByStatus(status);
        }
        return chamadoRepository.findAll();
    }

    // LISTAGEM 2: Histórico de chamados de um motorista específico (com filtro opcional por status)
    public List<ChamadoSocorro> listarPorMotorista(UUID motoristaId, ChamadoSocorro.StatusChamado status) {
        Usuario motorista = usuarioRepository.findById(motoristaId)
                .orElseThrow(() -> new RuntimeException("Motorista não encontrado!"));

        if (status != null) {
            return chamadoRepository.findByMotoristaAndStatus(motorista, status);
        }
        return chamadoRepository.findByMotorista(motorista);
    }

    // LISTAGEM 3: Atendimentos de um mecânico específico (com filtro opcional por status)
    public List<ChamadoSocorro> listarPorMecanico(UUID mecanicoId, ChamadoSocorro.StatusChamado status) {
        Usuario mecanico = usuarioRepository.findById(mecanicoId)
                .orElseThrow(() -> new RuntimeException("Mecânico não encontrado!"));

        if (status != null) {
            return chamadoRepository.findByMecanicoAndStatus(mecanico, status);
        }
        return chamadoRepository.findByMecanico(mecanico);
    }
}