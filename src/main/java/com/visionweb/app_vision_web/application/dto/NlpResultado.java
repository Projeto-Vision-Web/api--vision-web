package com.visionweb.app_vision_web.application.dto;

import com.visionweb.app_vision_web.domain.core.entities.Enum.Sentimento;

public record NlpResultado(
        Sentimento sentimento,
        double scoreSentimento,
        String cluster,
        double riscoTurnover
) {}