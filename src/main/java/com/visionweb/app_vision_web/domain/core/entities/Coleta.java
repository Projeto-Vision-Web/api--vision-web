package com.visionweb.app_vision_web.domain.core.entities;

import com.visionweb.app_vision_web.domain.core.entities.Enum.Canal;
import com.visionweb.app_vision_web.domain.core.entities.Enum.StatusColeta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "coleta")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_coleta")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_formulario", nullable = false)
    private Formulario formulario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_empresa", nullable = false)
    private Empresa empresa;

    @Column(name = "periodo_ref")
    private String periodoRef;

    @Enumerated(EnumType.STRING)
    private Canal canal = Canal.APP;

    @Enumerated(EnumType.STRING)
    private StatusColeta status = StatusColeta.CRIADA;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;

    @OneToMany(mappedBy = "coleta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ColetaParticipante> participantes;

    @OneToMany(mappedBy = "coleta")
    private List<Resposta> respostas;
}