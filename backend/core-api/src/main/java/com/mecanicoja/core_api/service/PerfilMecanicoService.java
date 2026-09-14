package com.mecanicoja.core_api.service;

import com.mecanicoja.core_api.domain.PerfilMecanico;
import com.mecanicoja.core_api.domain.Usuario;
import com.mecanicoja.core_api.dto.PerfilMecanicoRequest;
import com.mecanicoja.core_api.repository.PerfilMecanicoRepository;
import com.mecanicoja.core_api.repository.UsuarioRepository;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PerfilMecanicoService {

    private final PerfilMecanicoRepository perfilMecanicoRepository;
    private final UsuarioRepository usuarioRepository;
    private final GeometryFactory geometryFactory = new GeometryFactory(); // fábrica de gps

    public PerfilMecanicoService(PerfilMecanicoRepository perfilMecanicoRepository, UsuarioRepository usuarioRepository) {
        this.perfilMecanicoRepository = perfilMecanicoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public PerfilMecanico criarPerfil(UUID usuarioId, PerfilMecanicoRequest request) {
        Usuario mecanico = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Mecânico não encontrado!"));
        
        // mágica do postgis, transformando lat/long em Ponto
        // no sistema de coordenadas, é sempre (Longitude, Latitude) nessa ordem
        Point localizacao = geometryFactory.createPoint(new Coordinate(request.getLongitude(), request.getLatitude()));
        localizacao.setSRID(4326); // 4326 é o código mundial padrão do GPS
        PerfilMecanico perfil = new PerfilMecanico();
        perfil.setUsuario(mecanico);
        perfil.setEspecialidades(request.getEspecialidades());
        perfil.setRaioAtendimentoKm(request.getRaioAtendimentoKm());
        perfil.setDisponivel(true); // todo mecânico novo começa disponível
        perfil.setLocalizacao(localizacao);
        return perfilMecanicoRepository.save(perfil);
    }
}
