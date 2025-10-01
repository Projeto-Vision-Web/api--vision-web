package com.visionweb.app_vision_web.domain.core.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "login")
@AllArgsConstructor
@NoArgsConstructor
public class Login {

    private int id_login;
    private int id_usuario;
    private String nome;
    private String email;
    private String senha;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Usuario usuario;

}
