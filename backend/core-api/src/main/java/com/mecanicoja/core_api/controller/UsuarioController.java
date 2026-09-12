package com.mecanicoja.core_api.controller;

import com.mecanicoja.core_api.domain.Usuario;
import com.mecanicoja.core_api.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // diz que essa classe responde requesições da web
@RequestMapping("/api/usuarios") // endereço url para acesso
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // usada para criar algo novo
    @PostMapping
    public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario) {
        Usuario novoUsuario = usuarioService.cadastrarUsuario(usuario);

        // devolve 201 created e usuário salvo com id
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }


    // usada para ler informações
    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarTodos()); // devovle 200 OK
    }
}
