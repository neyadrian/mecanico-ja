package com.mecanicoja.core_api.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.locationtech.jts.geom.Point;

import java.util.UUID;

@Data
@Entity
@Table(name = "perfis_mecanico")
public class PerfilMecanico {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne // relacionamento, 1 perfil pertence a 1 usuario
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private String especialidades;

    @Column(nullable = false)
    private Float raioAtendimentoKm;

    @Column(nullable = false)
    private Boolean disponivel = false; // padrão é indisponível

    // esta coluna usará o PostGIS no banco para buscas ultra-rápidas por raio de distância
    @Column(columnDefinition = "geometry(Point, 4326)")
    private Point localizacao;
}
