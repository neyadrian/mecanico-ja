package com.mecanicoja.core_api.dto;
import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String senha;
}