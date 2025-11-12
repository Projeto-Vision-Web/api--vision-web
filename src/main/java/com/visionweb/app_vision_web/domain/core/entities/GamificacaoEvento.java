package com.visionweb.app_vision_web.domain.core.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "gamificacao_evento")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GamificacaoEvento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evento")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_colaborador", nullable = false)
    private Colaborador colaborador;

    @Column(nullable = false, length = 50)
    private String tipo; // ex: "RESPOSTA"

    @Column(nullable = false)
    private Integer pontuacao;

    @Column(name = "data_atribuicao")
    private LocalDateTime dataAtribuicao;
}