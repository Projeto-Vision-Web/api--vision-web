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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_login")
    private int id_login;

    private String email;
    private String senha;

    @OneToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario") // FK para Usuario
    private Usuario usuario;

    public Login(String email, String senha, Usuario usuario) {
        this.usuario = usuario;
        this.email = email;
        this.senha = senha;
    }

}
