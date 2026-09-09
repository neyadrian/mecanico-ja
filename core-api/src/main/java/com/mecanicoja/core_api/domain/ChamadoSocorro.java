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
    @JoinColumn(name = "diagnostico_id", nullable = false)
    private Diagnostico diagnostico;

    @ManyToOne
    @JoinColumn(name = "motorista_id", nullable = false)
    private Usuario motorista;

    @ManyToOne
    @JoinColumn(name = "mecanico_id")
    private Usuario mecanico;

    public enum StatusChamado {
        PENDENTE, ACEITO, RECUSADO, CONCLUIDO, CANCELADO
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusChamado status = StatusChamado.PENDENTE; // nasce como pendente por padrão

    @Column(nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    // preenche a data sozinho quando salva no banco
    @PrePersist
    protected void onCreate() {
        this.criadoEm = LocalDateTime.now();
    }
}