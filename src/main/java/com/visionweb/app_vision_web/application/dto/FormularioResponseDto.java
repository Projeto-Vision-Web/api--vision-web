package com.visionweb.app_vision_web.application.dto;

import java.util.List;

public record FormularioResponseDto(
        Integer id,
        String titulo,
        String descricao,
        int status,
        Integer idEmpresa,
        List<PerguntaResponseDto> pergunta
) {}
