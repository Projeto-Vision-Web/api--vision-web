package com.visionweb.app_vision_web.domain.core.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "feedback_processado")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "resposta")
public class FeedbackProcessado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_feedback_processado")
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_resposta", nullable = false)
    private Resposta resposta;

    @Column(name = "sentimento", length = 50)
    private String sentimento;

    @Column(name = "score_sentimento")
    private Double scoreSentimento;

    @Column(name = "cluster", length = 100)
    private String cluster;

    @Column(name = "risco_turnover")
    private Double riscoTurnover;
}
