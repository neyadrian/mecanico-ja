package com.mecanicoja.core_api.dto;

/**
 * DTO (Data Transfer Object) para o cadastro de um novo usuário.
 *
 * Por que isso é importante?
 * Antes, o endpoint de cadastro recebia o objeto Usuario inteiro,
 * incluindo campos internos como "id" e "criadoEm". Isso é uma má prática
 * porque o cliente não deveria ter controle sobre esses campos.
 *
 * Com esse DTO, o cliente envia APENAS o que ele deve enviar:
 * nome, email, senha, telefone e tipo. O resto é responsabilidade da API.
 */
public class RegistroRequest {

    private String nome;
    private String email;
    private String senha; // Será criptografada antes de salvar no banco!
    private String telefone;
    private String tipoUsuario; // MOTORISTA ou MECANICO

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getTipoUsuario() { return tipoUsuario; }
    public void setTipoUsuario(String tipoUsuario) { this.tipoUsuario = tipoUsuario; }
}
