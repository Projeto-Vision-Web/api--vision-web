package com.visionweb.app_vision_web.domain.core.entities;

import com.visionweb.app_vision_web.domain.core.entities.Enum.TipoPergunta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "pergunta")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pergunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pergunta")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_formulario", nullable = false)
    private Formulario formulario;

    @Column(name = "texto_pergunta", length = 500, nullable = false)
    private String textoPergunta;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPergunta tipo;

    @OneToMany(mappedBy = "pergunta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PerguntaOpcao> opcoes;
}