package com.visionweb.app_vision_web.domain.core.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "pergunta_opcao")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PerguntaOpcao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_opcao")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_pergunta", nullable = false)
    private Pergunta pergunta;

    @Column(name = "texto_opcao", nullable = false, length = 200)
    private String textoOpcao;

    @Column(name = "valor_num")
    private Integer valorNum;
}