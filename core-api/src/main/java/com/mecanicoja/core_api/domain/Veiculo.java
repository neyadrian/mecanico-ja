package com.mecanicoja.core_api.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "veiculos")
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // muitos veiculos podem pertenceer a 1 motorista
    @ManyToOne
    @JoinColumn(name = "motorista_id", nullable = false)
    private Usuario motorista;

    @Column(nullable = false, length = 50)
    private String marca;

    @Column(nullable = false, length = 50)
    private String modelo;

    @Column(nullable = false)
    private Integer ano;

    @Column(nullable = false, length = 30)
    private String tipoCombustivel;
}
