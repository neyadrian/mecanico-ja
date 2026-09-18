package com.mecanicoja.core_api.controller;

import com.mecanicoja.core_api.domain.ChamadoSocorro;
import com.mecanicoja.core_api.dto.ChamadoSocorroRequest;
import com.mecanicoja.core_api.service.ChamadoSocorroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}   