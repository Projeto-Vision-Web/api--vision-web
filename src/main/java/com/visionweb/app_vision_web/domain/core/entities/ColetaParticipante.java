package com.visionweb.app_vision_web.domain.core.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "coleta_participante")
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
class ColetaParticipanteId implements Serializable {
    @Column(name = "id_coleta")
    private Integer idColeta;

    @Column(name = "id_colaborador")
    private Integer idColaborador;
}