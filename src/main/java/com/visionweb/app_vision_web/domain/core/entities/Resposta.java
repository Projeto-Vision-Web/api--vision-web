package com.visionweb.app_vision_web.domain.core.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "resposta")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Resposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_resposta")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_pergunta", nullable = false)
    private Pergunta pergunta;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_colaborador", nullable = false)
    private Colaborador colaborador;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_coleta", nullable = false)
    private Coleta coleta;

    @Column(name = "valor_resposta", columnDefinition = "TEXT")
    private String valorResposta;

    @Column(name = "data_resposta")
    private LocalDateTime dataResposta;

    @OneToOne(mappedBy = "resposta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private AnaliseTexto analise;
}