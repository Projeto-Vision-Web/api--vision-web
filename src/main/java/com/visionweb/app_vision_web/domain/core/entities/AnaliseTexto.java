package com.visionweb.app_vision_web.domain.core.entities;

import com.visionweb.app_vision_web.domain.core.entities.Enum.Sentimento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "analise_texto")
@NoArgsConstructor
@AllArgsConstructor
public class AnaliseTexto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_analise")
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_resposta", nullable = false, unique = true)
    private Resposta resposta;

    @Enumerated(EnumType.STRING)
    private Sentimento sentimento;

    @Column(name = "score_sentimento")
    private Float scoreSentimento;

    @Column(name = "topicos_json", columnDefinition = "JSON")
    private String topicosJson;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;
}