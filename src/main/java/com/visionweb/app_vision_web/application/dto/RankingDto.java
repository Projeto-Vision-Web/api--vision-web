package com.visionweb.app_vision_web.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RankingDto {
    private String nomeColaborador;
    private Integer pontos;
    private Integer nivel;
}
