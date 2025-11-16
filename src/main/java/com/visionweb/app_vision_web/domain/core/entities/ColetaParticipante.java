package com.visionweb.app_vision_web.domain.core.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "coleta_participante")
public class ColetaParticipante {

    @EmbeddedId
    private ColetaParticipanteId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idColeta")
    @JoinColumn(name = "id_coleta")
    private Coleta coleta;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idColaborador")
    @JoinColumn(name = "id_colaborador")
    private Colaborador colaborador;

    @Column(name = "enviado_em")
    private LocalDateTime enviadoEm;

    @Column(name = "respondido_em")
    private LocalDateTime respondidoEm;
}

