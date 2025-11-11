package com.visionweb.app_vision_web.application.dto;

public record RespostaColetaResponseDto(
        Integer coletaId,
        Integer idColaborador,
        Integer totalRespondidas,
        String status
) {}