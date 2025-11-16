package com.visionweb.app_vision_web.application.mapper;

import com.visionweb.app_vision_web.application.dto.DepartamentoScoreDto;
import com.visionweb.app_vision_web.application.dto.InsightDto;
import com.visionweb.app_vision_web.domain.core.entities.FeedbackProcessado;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Funções de agregação e transformação para o relatório de clima.
 */
public final class RelatorioMapper {

    private RelatorioMapper() { }

    /**
     * Calcula o score médio de satisfação por departamento,
     * usando o score_sentimento dos feedbacks (normalizado para 0..100).
     */
    public static List<DepartamentoScoreDto> calcularDesempenhoPorDepartamento(
            List<FeedbackProcessado> feedbacks
    ) {
        if (feedbacks == null || feedbacks.isEmpty()) {
            return List.of();
        }

        // Agrupa por departamento
        Map<String, Double> mediaPorDepto = feedbacks.stream()
                .filter(f -> f.getResposta() != null
                        && f.getResposta().getColaborador() != null
                        && f.getResposta().getColaborador().getDepartamento() != null)
                .collect(Collectors.groupingBy(
                        f -> f.getResposta().getColaborador().getDepartamento(),
                        Collectors.averagingDouble(RelatorioMapper::scoreNormalizado0a100)
                ));

        // Converte para lista de DTO
        return mediaPorDepto.entrySet().stream()
                .map(e -> new DepartamentoScoreDto(e.getKey(), arredonda(e.getValue())))
                .sorted(Comparator.comparing(DepartamentoScoreDto::departamento))
                .toList();
    }

    /**
     * Gera alguns insights básicos com base nos feedbacks e taxa de participação.
     * É um MVP, mas já dá pra mostrar bem legal no front.
     */
    public static List<InsightDto> gerarInsights(
            List<FeedbackProcessado> feedbacks,
            double taxaParticipacao
    ) {
        List<InsightDto> lista = new ArrayList<>();

        // Insight 1: alta taxa de participação
        if (taxaParticipacao >= 80) {
            lista.add(new InsightDto(
                    "Alta Taxa de Participação",
                    String.format("Com %.0f%% de participação, a empresa demonstra forte engajamento dos colaboradores com o processo de pesquisa.",
                            taxaParticipacao),
                    "POSITIVO"
            ));
        }

        // Média de satisfação geral
        double mediaSatisfacao = feedbacks == null || feedbacks.isEmpty()
                ? 50.0
                : feedbacks.stream()
                .mapToDouble(RelatorioMapper::scoreNormalizado0a100)
                .average()
                .orElse(50.0);

        if (mediaSatisfacao < 65) {
            lista.add(new InsightDto(
                    "Atenção: Satisfação Abaixo do Ideal",
                    String.format("A satisfação média geral está em %.1f pontos. Recomenda-se investigar fatores de clima, carga de trabalho e liderança.",
                            mediaSatisfacao),
                    "ALERTA"
            ));
        } else if (mediaSatisfacao >= 75) {
            lista.add(new InsightDto(
                    "Boa Percepção de Clima",
                    String.format("A satisfação média geral está em %.1f pontos, indicando percepção positiva do ambiente organizacional.",
                            mediaSatisfacao),
                    "POSITIVO"
            ));
        }

        // Risco de turnover: vê se existe uma parcela relevante com risco alto
        if (feedbacks != null && !feedbacks.isEmpty()) {
            long total = feedbacks.size();
            long riscoAlto = feedbacks.stream()
                    .filter(f -> f.getRiscoTurnover() != null
                            && f.getRiscoTurnover() >= 0.6)
                    .count();

            double percRisco = total == 0 ? 0.0 : (riscoAlto * 100.0 / total);

            if (percRisco >= 15) {
                lista.add(new InsightDto(
                        "Risco de Turnover Elevado",
                        String.format("%.1f%% dos feedbacks indicam alto risco de desligamento. Recomenda-se ações de retenção e conversas individuais.",
                                percRisco),
                        "RISCO"
                ));
            }
        }

        // Se por algum motivo nada entrou, devolve um insight genérico
        if (lista.isEmpty()) {
            lista.add(new InsightDto(
                    "Clima Estável",
                    "Os indicadores não apontam riscos relevantes no período analisado. Manter as ações em andamento e acompanhar a evolução.",
                    "NEUTRO"
            ));
        }

        return lista;
    }

    // ===================== Helpers =====================

    /**
     * Converte scoreSentimento (-1..1) para escala 0..100.
     */
    private static double scoreNormalizado0a100(FeedbackProcessado f) {
        Double s = f.getScoreSentimento();
        if (s == null) {
            return 50.0; // neutro
        }
        // -1..1 -> 0..1 -> 0..100
        double v = (s + 1.0) / 2.0;
        return v * 100.0;
    }

    private static double arredonda(double v) {
        return Math.round(v * 10.0) / 10.0;
    }
}
