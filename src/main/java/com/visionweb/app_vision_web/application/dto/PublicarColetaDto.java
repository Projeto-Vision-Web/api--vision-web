package com.visionweb.app_vision_web.application.dto;

import com.visionweb.app_vision_web.domain.core.entities.Enum.Canal;

public record PublicarColetaDto(
        Integer idEmpresa,
        String periodoRef,
        Canal canal
) {}
