package com.visionweb.app_vision_web.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record FormularioCreateDto(
        @NotBlank String titulo,
        String descricao,
        int ativo,
        @NotNull Integer idEmpresa,
        Integer idUsuarioCriador, // opcional
        List<PerguntaCreateDto> perguntas
) {}
