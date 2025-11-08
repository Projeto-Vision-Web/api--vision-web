package com.visionweb.app_vision_web.domain.core.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "empresa")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_empresa;
    private String nome;
    private String setor;
    private String porte;
    private String emailContato;

}
