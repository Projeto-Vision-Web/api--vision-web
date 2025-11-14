package com.visionweb.app_vision_web.domain.core.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.visionweb.app_vision_web.domain.core.entities.Enum.TipoUsuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Table(name = "usuario")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private int id_usuario;
    private String nome;
    private String email;
    private boolean ativo;
    @Enumerated(EnumType.STRING)
    private TipoUsuario tipo_usuario;
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Login login;
    private Integer pontos = 0; // novo campo para gamificação

}
