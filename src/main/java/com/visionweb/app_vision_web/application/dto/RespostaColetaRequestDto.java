package com.visionweb.app_vision_web.application.dto;

import java.util.List;

public record RespostaColetaRequestDto(
        Integer idColaborador,
        List<ItemRespostaDto> respostas
) {}


