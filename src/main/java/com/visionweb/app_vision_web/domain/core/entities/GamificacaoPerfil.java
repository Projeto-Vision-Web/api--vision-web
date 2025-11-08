package com.visionweb.app_vision_web.domain.core.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "gamificacao_perfil")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GamificacaoPerfil {

    @Id
    @Column(name = "id_colaborador")
    private Integer idColaborador; // PK = FK

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id_colaborador")
    private Colaborador colaborador;

    private Integer pontos = 0;
    private Integer nivel = 1;

    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;
}