package com.visionweb.app_vision_web.application.dto;

import java.util.List;

public record RelatorioClimaResponseDto(
        HeaderIndicadoresDto header,
        EvolucaoIndicadoresDto evolucaoIndicadores,
        List<DepartamentoScoreDto> desempenhoPorDepartamento,
        DistribuicaoSatisfacaoDto distribuicaoSatisfacao,
        AnaliseComparativaDto analiseComparativa,
        List<InsightDto> insights
) {}
