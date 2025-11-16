package com.visionweb.app_vision_web.application.dto;

import com.visionweb.app_vision_web.domain.core.entities.FeedbackProcessado;

import java.util.List;

public record DistribuicaoSatisfacaoDto(
        double muitoSatisfeito,
        double satisfeito,
        double neutro,
        double insatisfeito,
        double muitoInsatisfeito
) {

    public static DistribuicaoSatisfacaoDto fromFeedbacks(List<FeedbackProcessado> feedbacks) {
        long total = feedbacks.size();
        if (total == 0) {
            return new DistribuicaoSatisfacaoDto(0,0,0,0,0);
        }

        long muitoSat = feedbacks.stream()
                .filter(f -> f.getScoreSentimento() != null && f.getScoreSentimento() > 0.6)
                .count();
        long sat = feedbacks.stream()
                .filter(f -> f.getScoreSentimento() != null &&
                        f.getScoreSentimento() > 0.2 && f.getScoreSentimento() <= 0.6)
                .count();
        long neutro = feedbacks.stream()
                .filter(f -> f.getScoreSentimento() != null &&
                        f.getScoreSentimento() >= -0.2 && f.getScoreSentimento() <= 0.2)
                .count();
        long insat = feedbacks.stream()
                .filter(f -> f.getScoreSentimento() != null &&
                        f.getScoreSentimento() < -0.2 && f.getScoreSentimento() >= -0.6)
                .count();
        long muitoInsat = feedbacks.stream()
                .filter(f -> f.getScoreSentimento() != null && f.getScoreSentimento() < -0.6)
                .count();

        return new DistribuicaoSatisfacaoDto(
                muitoSat * 100.0 / total,
                sat * 100.0 / total,
                neutro * 100.0 / total,
                insat * 100.0 / total,
                muitoInsat * 100.0 / total
        );
    }
}