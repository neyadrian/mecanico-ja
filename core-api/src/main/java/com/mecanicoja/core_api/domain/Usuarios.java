package com.mecanicoja.core_api.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data // cria getters e setters automaticamente
@Entity // diz ao spring que essa entidade vai virar uma tabela no bd
@Table(name = "usuarios")
public class Usuarios {

    
}
