package com.visionweb.app_vision_web.domain.core.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ColetaParticipanteId implements Serializable {
    @Column(name = "id_coleta")
    private Integer idColeta;

    @Column(name = "id_colaborador")
    private Integer idColaborador;
}
