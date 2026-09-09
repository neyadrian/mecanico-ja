package com.mecanicoja.core_api.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "diagnostico")
public class Diagnostico {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "motorista_id")
    private Usuario motorista;

    @ManyToOne
    @JoinColumn(name = "veiculo_id")
    private Veiculo veiculo;

    @Column(columnDefinition = "TEXT")
    private String sintomasRelatados;

    @Column(columnDefinition = "TEXT")
    private String iaPreDiagnostico;

    @Column(nullable = false)
    private LocalDateTime criadoEm;
}
