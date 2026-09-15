package com.mecanicoja.core_api.controller;

import com.mecanicoja.core_api.domain.Diagnostico;
import com.mecanicoja.core_api.dto.DiagnosticoRequest;
import com.mecanicoja.core_api.service.DiagnosticoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/diagnosticos")
public class DiagnosticoController {

    private final DiagnosticoService diagnosticoService;

    public DiagnosticoController(DiagnosticoService diagnosticoService) {
        this.diagnosticoService = diagnosticoService;
    }

    @PostMapping
    public ResponseEntity<Diagnostico> criar(@RequestBody DiagnosticoRequest request) {
        Diagnostico novo = diagnosticoService.criarDiagnostico(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(novo);
    }
}
