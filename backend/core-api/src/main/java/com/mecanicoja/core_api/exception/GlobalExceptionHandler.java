package com.mecanicoja.core_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Intercepta todas as exceções lançadas pelos Controllers e devolve
 * uma resposta JSON padronizada, em vez de uma página de erro feia do Tomcat.
 *
 * É como um "red carpet" de erros: todo problema que acontece na API
 * passa por aqui antes de chegar ao cliente, garantindo uma resposta elegante.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Trata erros de "não encontrado" (ex: usuário, veículo, chamado inexistente).
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {
        String mensagem = ex.getMessage() != null ? ex.getMessage() : "Ocorreu um erro inesperado.";

        // Define o status HTTP baseado na mensagem (convencção interna do nosso projeto)
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (mensagem.contains("não encontrado") || mensagem.contains("nao encontrado")) {
            status = HttpStatus.NOT_FOUND;
        } else if (mensagem.contains("Muito tarde") || mensagem.contains("não está mais")) {
            status = HttpStatus.CONFLICT;
        }

        return ResponseEntity.status(status).body(buildErrorBody(mensagem, status));
    }

    /**
     * Trata todos os outros erros genéricos que não foram capturados acima.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildErrorBody("Erro interno do servidor. Contate o suporte.", HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /**
     * Monta o corpo padrão do JSON de erro.
     * Todos os erros da API agora terão este mesmo formato consistente.
     */
    private Map<String, Object> buildErrorBody(String mensagem, HttpStatus status) {
        Map<String, Object> erro = new HashMap<>();
        erro.put("timestamp", LocalDateTime.now().toString());
        erro.put("status", status.value());
        erro.put("erro", status.getReasonPhrase());
        erro.put("mensagem", mensagem);
        return erro;
    }
}
