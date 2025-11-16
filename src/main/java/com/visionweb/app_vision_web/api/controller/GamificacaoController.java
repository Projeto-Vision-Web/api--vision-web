package com.visionweb.app_vision_web.api.controller;

import com.visionweb.app_vision_web.application.dto.RankingDto;
import com.visionweb.app_vision_web.domain.contracts.service.GamificacaoService;
import com.visionweb.app_vision_web.domain.core.entities.GamificacaoPerfil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gamificacao")
@RequiredArgsConstructor
public class GamificacaoController {

    private final GamificacaoService gamificacaoService;

    // Somar +10 pontos ao responder formulário
    @PostMapping("/resposta/{idColaborador}")
    public ResponseEntity<Void> registrarResposta(@PathVariable Integer idColaborador) {
        gamificacaoService.registrarEventoResposta(idColaborador);
        return ResponseEntity.ok().build();
    }

    // Ranking Top 10
    @GetMapping("/ranking")
    public ResponseEntity<List<RankingDto>> getRanking() {
        return ResponseEntity.ok(gamificacaoService.listarRankingTop10());
    }
}
