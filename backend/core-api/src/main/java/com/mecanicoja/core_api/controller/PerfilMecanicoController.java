package com.mecanicoja.core_api.controller;

import com.mecanicoja.core_api.domain.PerfilMecanico;
import com.mecanicoja.core_api.dto.PerfilMecanicoRequest;
import com.mecanicoja.core_api.service.PerfilMecanicoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/mecanicos/{usuarioId}/perfil")
public class PerfilMecanicoController {

    private final PerfilMecanicoService perfilService;

    public PerfilMecanicoController(PerfilMecanicoService perfilService) {
        this.perfilService = perfilService;
    }

    @PostMapping
    public ResponseEntity<PerfilMecanico> criar(@PathVariable UUID usuarioId, @RequestBody PerfilMecanicoRequest request) {

        // recebemos o DTO cru e o Service faz o trabalho sujo
        PerfilMecanico perfil = perfilService.criarPerfil(usuarioId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(perfil);
    }
}