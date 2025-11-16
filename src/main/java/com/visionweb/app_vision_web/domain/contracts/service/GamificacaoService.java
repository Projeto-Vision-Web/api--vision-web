package com.visionweb.app_vision_web.domain.contracts.service;

import com.visionweb.app_vision_web.application.dto.RankingDto;
import com.visionweb.app_vision_web.domain.core.entities.GamificacaoPerfil;

import java.util.List;

public interface GamificacaoService {
    void registrarEventoResposta(Integer idColaborador);
    List<RankingDto> listarRankingTop10();
}
