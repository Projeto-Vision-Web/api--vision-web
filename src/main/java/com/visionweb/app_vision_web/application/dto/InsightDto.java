package com.visionweb.app_vision_web.application.dto;

public record InsightDto(
        String titulo,
        String descricao,
        String tipo // ex.: "ALTA", "ATENCAO", "RISCO"
) {}
