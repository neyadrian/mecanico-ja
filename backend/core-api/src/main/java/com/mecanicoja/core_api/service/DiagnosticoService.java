package com.mecanicoja.core_api.service;

import com.mecanicoja.core_api.domain.Diagnostico;
import com.mecanicoja.core_api.domain.Usuario;
import com.mecanicoja.core_api.domain.Veiculo;
import com.mecanicoja.core_api.dto.DiagnosticoRequest;
import com.mecanicoja.core_api.repository.DiagnosticoRepository;
import com.mecanicoja.core_api.repository.UsuarioRepository;
import com.mecanicoja.core_api.repository.VeiculoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate; // O Postman do Java!

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
public class DiagnosticoService {

    private final DiagnosticoRepository diagnosticoRepository;
    private final UsuarioRepository usuarioRepository;
    private final VeiculoRepository veiculoRepository;

    public DiagnosticoService(DiagnosticoRepository diagnosticoRepository,
                              UsuarioRepository usuarioRepository,
                              VeiculoRepository veiculoRepository) {
        this.diagnosticoRepository = diagnosticoRepository;
        this.usuarioRepository = usuarioRepository;
        this.veiculoRepository = veiculoRepository;
    }

    public Diagnostico criarDiagnostico(DiagnosticoRequest request) {
        Usuario motorista = usuarioRepository.findById(request.getMotoristaId())
                .orElseThrow(() -> new RuntimeException("Motorista não encontrado!"));

        Veiculo veiculo = veiculoRepository.findById(request.getVeiculoId())
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado!"));

        Diagnostico diagnostico = new Diagnostico();
        diagnostico.setMotorista(motorista);
        diagnostico.setVeiculo(veiculo);
        diagnostico.setSintomasRelatados(request.getSintomasRelatados());
        diagnostico.setCriadoEm(LocalDateTime.now());

        // --- A MÁGICA DOS MICROSSERVIÇOS COMEÇA AQUI ---
        try {
            RestTemplate restTemplate = new RestTemplate();
            String pythonUrl = "http://localhost:8000/api/ia/analisar";

            // Prepara o JSON para enviar para o Python
            Map<String, String> payloadParaPython = Map.of("sintomas", request.getSintomasRelatados());

            // Dispara o POST e pega a resposta do Google Gemini
            Map respostaDoPython = restTemplate.postForObject(pythonUrl, payloadParaPython, Map.class);

            // Extrai o texto brilhante da IA e injeta no diagnóstico!
            if (respostaDoPython != null && respostaDoPython.containsKey("pre_diagnostico")) {
                diagnostico.setIaPreDiagnostico(respostaDoPython.get("pre_diagnostico").toString());
            }
        } catch (Exception e) {
            System.out.println("Erro ao chamar o servidor de IA: " + e.getMessage());
            diagnostico.setIaPreDiagnostico("IA indisponível no momento.");
        }
        // -----------------------------------------------

        return diagnosticoRepository.save(diagnostico);
    }
}