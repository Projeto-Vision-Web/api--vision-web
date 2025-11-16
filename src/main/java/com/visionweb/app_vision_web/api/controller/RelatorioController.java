package com.visionweb.app_vision_web.api.controller;

import com.visionweb.app_vision_web.application.dto.RelatorioClimaResponseDto;
import com.visionweb.app_vision_web.domain.contracts.service.RelatorioClimaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/relatorios")
public class RelatorioController {

    private final RelatorioClimaService relatorioClimaService;

    public RelatorioController(RelatorioClimaService relatorioClimaService) {
        this.relatorioClimaService = relatorioClimaService;
    }

    // Ex: /api/relatorios/clima?empresaId=1&periodoRef=2025-01
    @GetMapping("/clima")
    public ResponseEntity<RelatorioClimaResponseDto> getRelatorioClima(
            @RequestParam Integer empresaId,
            @RequestParam String periodoRef // ou coletaId
    ) {
        RelatorioClimaResponseDto relatorio =
                relatorioClimaService.gerarRelatorioClima(empresaId, periodoRef);

        return ResponseEntity.ok(relatorio);
    }
}

