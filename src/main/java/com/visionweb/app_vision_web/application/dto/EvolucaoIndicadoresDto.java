package com.visionweb.app_vision_web.application.dto;

import java.util.List;

public record EvolucaoIndicadoresDto(
        List<PontoEvolucaoDto> pontos
) {

    /**
     * Ponto da série temporal.
     */
    public record PontoEvolucaoDto(
            String mes,            // "Jan", "Fev", ...
            double satisfacao,     // 0..100
            double engajamento,    // 0..100
            double comunicacao     // 0..100
    ) {}

    /**
     * Dados mockados para MVP / protótipo.
     * Você pode trocar depois para montar a partir das coletas reais.
     */
    public static EvolucaoIndicadoresDto mock() {
        return new EvolucaoIndicadoresDto(
                List.of(
                        new PontoEvolucaoDto("Jan", 68, 65, 66),
                        new PontoEvolucaoDto("Fev", 70, 67, 68),
                        new PontoEvolucaoDto("Mar", 72, 69, 70),
                        new PontoEvolucaoDto("Abr", 74, 71, 72),
                        new PontoEvolucaoDto("Mai", 76, 73, 74),
                        new PontoEvolucaoDto("Jun", 78, 75, 76)
                )
        );
    }
}
