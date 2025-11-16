package com.visionweb.app_vision_web.application.dto;

import java.util.List;

public record AnaliseComparativaDto(
        List<CategoriaComparativaDto> categorias
) {

    public record CategoriaComparativaDto(
            String categoria,      // "Liderança", "Ambiente", ...
            double periodoAtual,   // 0..100
            double periodoAnterior // 0..100
    ) {}

    /**
     * Dados mockados para MVP.
     * Depois você pode trocar para montar comparando duas coletas.
     */
    public static AnaliseComparativaDto mock() {
        return new AnaliseComparativaDto(
                List.of(
                        new CategoriaComparativaDto("Liderança",      82, 75),
                        new CategoriaComparativaDto("Ambiente",       78, 74),
                        new CategoriaComparativaDto("Benefícios",     70, 68),
                        new CategoriaComparativaDto("Crescimento",    76, 72),
                        new CategoriaComparativaDto("Comunicação",    80, 72),
                        new CategoriaComparativaDto("Reconhecimento", 58, 60)
                )
        );
    }
}
