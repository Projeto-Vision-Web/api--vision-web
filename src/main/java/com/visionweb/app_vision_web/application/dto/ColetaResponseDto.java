package com.visionweb.app_vision_web.application.dto;

import com.visionweb.app_vision_web.domain.core.entities.Coleta;
import com.visionweb.app_vision_web.domain.core.entities.Enum.Canal;

import java.time.LocalDateTime;

public record ColetaResponseDto(
        Integer id,
        Integer idFormulario,
        Integer idEmpresa,
        String periodoRef,
        Canal canal,
        String status,
        LocalDateTime criadoEm
) {
    public static ColetaResponseDto fromEntity(Coleta c) {
        return new ColetaResponseDto(
                c.getId(),
                c.getFormulario() != null ? c.getFormulario().getId() : null,
                c.getEmpresa() != null ? c.getEmpresa().getId_empresa() : null,
                c.getPeriodoRef(),
                c.getCanal() != null ? c.getCanal(): null,
                c.getStatus() != null ? c.getStatus().name() : null,
                c.getCriadoEm()
        );
    }
}