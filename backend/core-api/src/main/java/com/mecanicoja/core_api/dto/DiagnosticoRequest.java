package com.mecanicoja.core_api.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class DiagnosticoRequest {
    private UUID motoristaId;
    private UUID veiculoId;
    private String sintomasRelatados;
}
