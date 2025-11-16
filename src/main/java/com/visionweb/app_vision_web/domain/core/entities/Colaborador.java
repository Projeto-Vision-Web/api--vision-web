package com.visionweb.app_vision_web.domain.core.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "colaborador")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Colaborador {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_colaborador")
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false, unique = true)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_empresa", nullable = false)
    private Empresa empresa;

    private String departamento;
    private String cargo;

    @Column(name = "data_admissao")
    private LocalDate dataAdmissao;

    @Column(name = "perfil_geracional")
    private String perfilGeracional;

    private Boolean ativo = Boolean.TRUE;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;

    @OneToOne(mappedBy = "colaborador", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private GamificacaoPerfil gamificacaoPerfil;
}