package com.visionweb.app_vision_web.application.services;

import com.visionweb.app_vision_web.application.dto.*;
import com.visionweb.app_vision_web.application.mapper.RelatorioMapper;
import com.visionweb.app_vision_web.domain.contracts.repository.ColaboradorRepository;
import com.visionweb.app_vision_web.domain.contracts.repository.ColetaRepository;
import com.visionweb.app_vision_web.domain.contracts.repository.FeedbackProcessadoRepository;
import com.visionweb.app_vision_web.domain.contracts.service.RelatorioClimaService;
import com.visionweb.app_vision_web.domain.core.entities.Coleta;
import com.visionweb.app_vision_web.domain.core.entities.FeedbackProcessado;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelatorioClimaServiceImpl implements RelatorioClimaService {

    private final FeedbackProcessadoRepository feedbackRepo;
    private final ColaboradorRepository colaboradorRepo;
    private final ColetaRepository coletaRepo;

    public RelatorioClimaServiceImpl(FeedbackProcessadoRepository feedbackRepo,
                                     ColaboradorRepository colaboradorRepo,
                                     ColetaRepository coletaRepo) {
        this.feedbackRepo = feedbackRepo;
        this.colaboradorRepo = colaboradorRepo;
        this.coletaRepo = coletaRepo;
    }

    @Override
    public RelatorioClimaResponseDto gerarRelatorioClima(Integer empresaId, String periodoRef) {

        // 1) encontra a coleta desse período/empresa
        Coleta coleta = coletaRepo
                .findByEmpresaAndPeriodoRef(empresaId, periodoRef)
                .orElseThrow(() -> new RuntimeException("Coleta não encontrada"));

        // 2) busca todos os feedbacks processados dessa coleta
        List<FeedbackProcessado> feedbacks =
                feedbackRepo.findByColetaId(coleta.getId());

        // 3) total de colaboradores da empresa
        long totalColaboradores = colaboradorRepo.countByEmpresa(empresaId);

        // ======= EXEMPLOS DE CÁLCULOS =======

        // taxa de participação (colabs que responderam / total)
        long colaboradoresResponderam = feedbacks.stream()
                .map(fp -> fp.getResposta().getColaborador().getId())
                .distinct()
                .count();

        double taxaParticipacao = totalColaboradores == 0 ? 0.0 :
                (colaboradoresResponderam * 100.0) / totalColaboradores;

        // índice de satisfação (normaliza score_sentimento -1..1 para 0..100)
        double indiceSatisfacao = feedbacks.stream()
                .mapToDouble(fp -> {
                    Double s = fp.getScoreSentimento();
                    if (s == null) return 0.5; // neutro
                    return (s + 1) / 2.0;      // -1..1 -> 0..1
                })
                .average()
                .orElse(0.5) * 100.0;

        // engajamento médio (pode usar a própria taxa de participação ou média de respostas)
        double engajamentoMedio = taxaParticipacao; // MVP: reaproveita

        // NPS organizacional (MVP: se você tiver uma pergunta NPS, calcula à parte)
        double nps = 45; // placeholder / cálculo futuro

        // Distribuição de satisfação (muito satisfeito, satisfeito...)
        DistribuicaoSatisfacaoDto distribuicao = DistribuicaoSatisfacaoDto.fromFeedbacks(feedbacks);

        // Desempenho por departamento
        List<DepartamentoScoreDto> desempenhoPorDepto =
                RelatorioMapper.calcularDesempenhoPorDepartamento(feedbacks);

        // Evolução e análise comparativa: MVP pode deixar mockado
        EvolucaoIndicadoresDto evolucao = EvolucaoIndicadoresDto.mock();
        AnaliseComparativaDto analiseComparativa = AnaliseComparativaDto.mock();

        // Insights com base em clusters + sentimento negativo
        List<InsightDto> insights = RelatorioMapper.gerarInsights(feedbacks, taxaParticipacao);

        return new RelatorioClimaResponseDto(
                new HeaderIndicadoresDto(
                        round(taxaParticipacao),
                        round(indiceSatisfacao),
                        round(engajamentoMedio),
                        (int) nps
                ),
                evolucao,
                desempenhoPorDepto,
                distribuicao,
                analiseComparativa,
                insights
        );
    }

    private double round(double v) {
        return Math.round(v * 10.0) / 10.0;
    }
}
