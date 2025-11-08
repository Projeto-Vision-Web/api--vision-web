package com.visionweb.app_vision_web.application.dto;

public record FormularioResponseDto(
        Integer id,
        String titulo,
        String descricao,
        int status,
        Integer idEmpresa
) {}
