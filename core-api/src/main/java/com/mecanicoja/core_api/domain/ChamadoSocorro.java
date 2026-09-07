package com.mecanicoja.core_api.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "chamado_socorro")
public class ChamadoSocorro {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "diagnostico_id")
    private Diagnostico diagnostico;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario motorista;

    @ManyToOne
    @JoinColumn(name = "usuario_id",  nullable = false)
    private Usuario mecanico;

    private Enum status {
        ENDENTE, ACEITO, RECUSADO, CONCLUIDO, CANCELADO;
    }

    private LocalDateTime criadoEm;
}
