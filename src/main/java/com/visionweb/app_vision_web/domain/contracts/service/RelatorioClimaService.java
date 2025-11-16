package com.visionweb.app_vision_web.domain.contracts.service;

import com.visionweb.app_vision_web.application.dto.RelatorioClimaResponseDto;

public interface RelatorioClimaService {
    RelatorioClimaResponseDto gerarRelatorioClima(Integer empresaId, String periodoRef);
}
