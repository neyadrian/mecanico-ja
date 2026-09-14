package com.mecanicoja.core_api.dto;

import lombok.Data;

@Data
public class PerfilMecanicoRequest {
    private String especialidades;
    private float raioAtendimentoKm;
    private double latitude;
    private double longitude;
}
