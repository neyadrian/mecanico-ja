package com.mecanicoja.core_api.controller;

import com.mecanicoja.core_api.domain.Veiculo;
import com.mecanicoja.core_api.service.VeiculoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/motoristas/{motoristaId}/veiculos")
public class VeiculoController {

    private final VeiculoService veiculoService;

    public VeiculoController(VeiculoService veiculoService) {
        this.veiculoService = veiculoService;
    }

    @PostMapping
    public ResponseEntity<Veiculo> cadastrar(@PathVariable UUID motoristaId, @RequestBody Veiculo veiculo){
        Veiculo novoVeiculo = veiculoService.cadastrarVeiculo(motoristaId, veiculo);

        return ResponseEntity.status(HttpStatus.CREATED).body(novoVeiculo);
    }

    @GetMapping
    public ResponseEntity<List<Veiculo>> listar(@PathVariable UUID motoristaId) {
        return ResponseEntity.ok(veiculoService.buscarVeiculos(motoristaId));
    }
}
