package com.visionweb.app_vision_web.domain.core.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "formulario")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Formulario {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_formulario")
    private Integer id;

    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    private int ativo; // simples no MVP

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private Usuario criadoPor;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_empresa", nullable = false)
    private Empresa empresa;

    @OneToMany(mappedBy = "formulario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pergunta> perguntas;
}
