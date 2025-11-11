package com.visionweb.app_vision_web.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PerguntaCreateDto(
        @NotNull Integer idFormulario,
        @NotBlank String textoPergunta,
        @NotBlank String tipo,
        List<OpcaoDto> opcoes
) {}

