package com.mecanicoja.core_api.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class ChamadoSocorroRequest {
    private UUID motoristaId;
    private UUID diagnosticoId;
    // Não recebemos mecânico nem status aqui, porque o sistema é quem controla isso
}