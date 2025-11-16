package com.visionweb.app_vision_web.application.dto;

import java.util.List;

public record PerguntaResponseDto(
        Integer id,
        String textoPergunta,
        String tipo,
        Integer idFormulario,
        List<PerguntaOpcaoResponseDto> opcoes
) {}
