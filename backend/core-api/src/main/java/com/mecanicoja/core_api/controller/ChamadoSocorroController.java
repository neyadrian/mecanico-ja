package com.mecanicoja.core_api.controller;

import com.mecanicoja.core_api.domain.ChamadoSocorro;
import com.mecanicoja.core_api.dto.ChamadoSocorroRequest;
import com.mecanicoja.core_api.service.ChamadoSocorroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/chamados")
public class ChamadoSocorroController {

    private final ChamadoSocorroService chamadoService;

    public ChamadoSocorroController(ChamadoSocorroService chamadoService) {
        this.chamadoService = chamadoService;
    }

    // Rota para abrir chamado (Recebe o DTO em JSON)
    @PostMapping
    public ResponseEntity<ChamadoSocorro> abrirChamado(@RequestBody ChamadoSocorroRequest request) {
        ChamadoSocorro chamado = chamadoService.abrirChamado(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(chamado);
    }

    // Rota para o Mecânico aceitar o chamado (Atualização = PUT)
    @PutMapping("/{chamadoId}/aceitar/{mecanicoId}")
    public ResponseEntity<ChamadoSocorro> aceitarChamado(@PathVariable UUID chamadoId, @PathVariable UUID mecanicoId) {
        ChamadoSocorro chamado = chamadoService.aceitarChamado(chamadoId, mecanicoId);
        return ResponseEntity.ok(chamado);
    }

    // Rota para cancelar chamado (Atualização = PUT / DELETE semântico)
    @PutMapping("/{chamadoId}/cancelar")
    public ResponseEntity<ChamadoSocorro> cancelarChamado(@PathVariable UUID chamadoId) {
        ChamadoSocorro chamado = chamadoService.cancelarChamado(chamadoId);
        return ResponseEntity.ok(chamado);
    }

    // Rota para o Mecânico concluir o atendimento (Atualização = PUT)
    @PutMapping("/{chamadoId}/concluir")
    public ResponseEntity<ChamadoSocorro> concluirChamado(@PathVariable UUID chamadoId) {
        ChamadoSocorro chamado = chamadoService.concluirChamado(chamadoId);
        return ResponseEntity.ok(chamado);
    }

    // Listar todos os chamados com filtro opcional por status (?status=PENDENTE)
    @GetMapping
    public ResponseEntity<List<ChamadoSocorro>> listarChamados(
            @RequestParam(required = false) ChamadoSocorro.StatusChamado status) {
        List<ChamadoSocorro> chamados = chamadoService.listarChamados(status);
        return ResponseEntity.ok(chamados);
    }

    // Listar chamados de um motorista específico (?status=ACEITO)
    @GetMapping("/motorista/{motoristaId}")
    public ResponseEntity<List<ChamadoSocorro>> listarPorMotorista(
            @PathVariable UUID motoristaId,
            @RequestParam(required = false) ChamadoSocorro.StatusChamado status) {
        List<ChamadoSocorro> chamados = chamadoService.listarPorMotorista(motoristaId, status);
        return ResponseEntity.ok(chamados);
    }

    // Listar chamados de um mecânico específico (?status=CONCLUIDO)
    @GetMapping("/mecanico/{mecanicoId}")
    public ResponseEntity<List<ChamadoSocorro>> listarPorMecanico(
            @PathVariable UUID mecanicoId,
            @RequestParam(required = false) ChamadoSocorro.StatusChamado status) {
        List<ChamadoSocorro> chamados = chamadoService.listarPorMecanico(mecanicoId, status);
        return ResponseEntity.ok(chamados);
    }
}